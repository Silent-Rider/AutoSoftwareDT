package lab5scala;

class UniversalLinkedList[T] (val userType: UserType[T]) {
  
  private class Node[N] (var value: N) {
    var previous: Option[Node[N]] = None
    var next: Option[Node[N]] = None
  }

  private var first: Option[Node[T]] = None
  private var last: Option[Node[T]] = None
  private var size: Int = 0
  
  
}
