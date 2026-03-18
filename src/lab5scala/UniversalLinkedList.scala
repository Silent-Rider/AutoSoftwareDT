package lab5scala;

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

}
