package lab5scala;

import java.util.Comparator

class UniversalLinkedList[T] (val userType: UserType[T]) {

  private class Node (var value: T) {
    var previous: Node = null
    var next: Node = null
  }

  private var first: Node = null
  private var last: Node = null
  private var size: Int = 0

  def this(userType: UserType[T], values: T*) = {
    this(userType)
    values.foreach(addLast)
  }

  def addFirst(value: T): Unit = {
    val newNode: Node = new Node(value)
    if (first == null) {
      first = newNode
      last = newNode
    } else {
      newNode.next = first
      first.previous = newNode
      first = newNode
    }
    size += 1
  }

  def addLast (value: T): Unit = {
    val newNode: Node = new Node(value)
    if (last == null) {
      first = newNode
    } else {
      last.next = newNode
      newNode.previous = last
    }
    last = newNode
    size += 1
  }

  def get (index: Int): T  = {
    if (index < 0 || index >= size) {
    throw new IndexOutOfBoundsException (s"Index: $index, Size: $size")
  }
    val current: Node = getNodeByIndex (index)
    current.value
  }

  private def getNodeByIndex(index: Int): Node = {
    var current: Node = null
    if (index < size / 2) {
      current = first
      for (i <- 0 until index) {
        current = current.next
      }
    } else {
      current = last
      for (i <- (size - 1) to (index + 1) by -1) {
        current = current.previous
      }
    }
    current
  }

  def add(index: Int, value: T): Unit = {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(s"Index: $index, Size: $size")
    }
    if (index == size) {
      addLast(value)
      return
    }
    if (index == 0) {
      addFirst(value)
      return
    }

    val nextNode = getNodeByIndex(index)
    val prevNode = nextNode.previous
    val newNode = new Node(value)
    
    prevNode.next = newNode
    newNode.previous = prevNode
    newNode.next = nextNode
    nextNode.previous = newNode

    size += 1
  }

  def set(index: Int, value: T): T = {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(s"Index: $index, Size: $size")
    }
    val nodeToSet = getNodeByIndex(index)
    val oldValue = nodeToSet.value

    nodeToSet.value = value
    oldValue
  }

  def remove(index: Int): T = {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(s"Index: $index, Size: $size")
    }

    val nodeToRemove = getNodeByIndex(index)
    val removedValue = nodeToRemove.value
    
    if (nodeToRemove.eq(first) && nodeToRemove.eq(last)) {
      first = null
      last = null
    } else if (nodeToRemove.eq(first)) {
      first = nodeToRemove.next
      first.previous = null
    } else if (nodeToRemove.eq(last)) {
      last = nodeToRemove.previous
      last.next = null
    } else {
      val prev = nodeToRemove.previous
      val next = nodeToRemove.next
      prev.next = next
      next.previous = prev
    }

    size -= 1
    removedValue
  }

  def getSize: Int = size

  def sort(): Unit = {
    if (size < 2) return

    first = mergeSort(first)
    last = first
    
    while (last != null && last.next != null) {
      last = last.next
    }
  }

  private def mergeSort(head: Node): Node = {
    if (head == null || head.next == null) {
      return head
    }

    val middle = getMiddle(head)
    val nextOfMiddle = middle.next
    middle.next = null

    val left = mergeSort(head)
    val right = mergeSort(nextOfMiddle)

    sortAndMergeLists(left, right)
  }

  private def getMiddle(head: Node): Node = {
    var slow: Node = head
    var fast: Node = head
    
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    slow
  }

  private def sortAndMergeLists(a: Node, b: Node): Node = {
    if (a == null) return b
    if (b == null) return a

    var result: Node = null
    val comparator: Comparator[T] = userType.getTypeComparator

    val diff = comparator.compare(a.value, b.value)

    if (diff <= 0) {
      result = a
      result.next = sortAndMergeLists(a.next, b)
    } else {
      result = b
      result.next = sortAndMergeLists(a, b.next)
    }

    if (result.next != null) {
      result.next.previous = result
    }

    result.previous = null
    result
  }

  def forEach(action: T => Unit): Unit = {
    var current = first
    while (current != null) {
      action(current.value)
      current = current.next
    }
  }

  def printList(): Unit = {
    forEach { value => print(s"$value ") }
    println()
  }
}
