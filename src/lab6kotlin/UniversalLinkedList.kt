package lab6kotlin

class UniversalLinkedList<T> {

    private class Node<T>(var value: T) {
        var previous: Node<T>? = null
        var next: Node<T>? = null
    }

    private val userType: UserType<T>
    private var first: Node<T>? = null
    private var last: Node<T>? = null
    private var size: Int = 0

    constructor(userType: UserType<T>) {
        this.userType = userType
    }

    constructor(userType: UserType<T>, vararg values: T) {
        this.userType = userType
        values.forEach(::addLast)
    }

    fun addFirst(value: T) {
        val newNode: Node<T> = Node(value)
        if (first == null) {
            first = newNode
            last = newNode
        } else {
            newNode.next = first
            first!!.previous = newNode
            first = newNode
        }
        size++
    }

    fun addLast(value: T) {
        val newNode: Node<T> = Node(value)
        if (last == null) {
            first = newNode
        } else {
            last!!.next = newNode
            newNode.previous = last
        }
        last = newNode
        size++
    }

    fun get(index: Int): T {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        val current: Node<T>? = getNodeByIndex(index)
        return current!!.value
    }

    private fun getNodeByIndex(index: Int): Node<T>? {
        var current: Node<T>?
        if (index < size / 2) {
            current = first
            for (i in 0 until index) {
                current = current!!.next
            }
        } else {
            current = last
            for (i in size - 1 downTo index + 1) {
                current = current!!.previous
            }
        }
        return current
    }

    fun add(index: Int, value: T) {
        if (index < 0 || index > size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        if (index == size) {
            addLast(value)
            return
        }
        if (index == 0) {
            addFirst(value)
            return
        }

        val nextNode: Node<T> = getNodeByIndex(index)!!
        val prevNode: Node<T>? = nextNode.previous
        val newNode: Node<T> = Node(value)

        prevNode!!.next = newNode
        newNode.previous = prevNode
        newNode.next = nextNode
        nextNode.previous = newNode

        size++
    }

    fun set(index: Int, value: T): T {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        val nodeToSet: Node<T> = getNodeByIndex(index)!!
        val oldValue: T = nodeToSet.value

        nodeToSet.value = value
        return oldValue
    }

    fun remove(index: Int): T {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }

        val nodeToRemove: Node<T> = getNodeByIndex(index)!!
        val removedValue: T = nodeToRemove.value

        if (nodeToRemove === first && nodeToRemove === last) {
            first = null
            last = null
        } else if (nodeToRemove === first) {
            first = nodeToRemove.next
            first!!.previous = null
        } else if (nodeToRemove === last) {
            last = nodeToRemove.previous
            last!!.next = null
        } else {
            val prev: Node<T>? = nodeToRemove.previous
            val next: Node<T>? = nodeToRemove.next
            prev!!.next = next
            next!!.previous = prev
        }

        size--
        return removedValue
    }

    fun size(): Int = size

    fun sort() {
        if (size < 2) return
        first = mergeSort(first)
        last = first
        while (last != null && last!!.next != null) {
            last = last!!.next
        }
    }

    private fun mergeSort(head: Node<T>?): Node<T>? {
        if (head == null || head.next == null) {
            return head
        }

        val middle: Node<T> = getMiddle(head)!!
        val nextOfMiddle: Node<T>? = middle.next
        middle.next = null

        val left: Node<T>? = mergeSort(head)
        val right: Node<T>? = mergeSort(nextOfMiddle)

        return sortAndMergeLists(left, right)
    }

    private fun getMiddle(head: Node<T>?): Node<T>? {
        var slow: Node<T>? = head
        var fast: Node<T>? = head

        while (fast != null && fast.next != null && fast.next!!.next != null) {
            slow = slow?.next
            fast = fast.next!!.next
        }
        return slow
    }

    private fun sortAndMergeLists(a: Node<T>?, b: Node<T>?): Node<T>? {
        if (a == null) return b
        if (b == null) return a

        var result: Node<T>
        val comparator: Comparator<T> = userType.getTypeComparator()

        val diff: Int = comparator.compare(a.value, b.value)

        if (diff <= 0) {
            result = a
            result.next = sortAndMergeLists(a.next, b)
        } else {
            result = b
            result.next = sortAndMergeLists(a, b.next)
        }

        result.next!!.previous = result
        result.previous = null
        return result
    }

    fun forEach(action: (T) -> Unit) {
        var current: Node<T>? = first
        while (current != null) {
            action(current.value)
            current = current.next
        }
    }

    fun printList() {
        forEach { value -> print("$value ") }
        println()
    }
}