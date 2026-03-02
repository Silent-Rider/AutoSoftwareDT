package lab2;

public class IntegerLinkedList {

    private Node first;
    private Node last;
    private int size;

    public IntegerLinkedList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void addFirst(int value) {
        Node newNode = new Node(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.setNext(first);
            first.setPrevious(newNode);
            first = newNode;
        }
        size++;
    }

    public void addLast(int value) {
        Node newNode = new Node(value);
        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrevious(last);
            last = newNode;
        }
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node current = getNodeByIndex(index);
        return current.getValue();
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            addLast(value);
            return;
        }
        if (index == 0) {
            addFirst(value);
            return;
        }

        Node nextNode = getNodeByIndex(index);
        Node prevNode = nextNode.getPrevious();
        Node newNode = new Node(value);

        prevNode.setNext(newNode);
        newNode.setPrevious(prevNode);
        newNode.setNext(nextNode);
        nextNode.setPrevious(newNode);

        size++;
    }

    public int set(int index, int value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node nodeToSet = getNodeByIndex(index);
        int oldValue = nodeToSet.getValue();

        nodeToSet.setValue(value);
        return oldValue;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node nodeToRemove = getNodeByIndex(index);
        int removedValue = nodeToRemove.getValue();

        if (nodeToRemove == first && nodeToRemove == last) {
            first = null;
            last = null;
        } else if (nodeToRemove == first) {
            first = nodeToRemove.getNext();
            first.setPrevious(null);
        } else if (nodeToRemove == last) {
            last = nodeToRemove.getPrevious();
            last.setNext(null);
        } else {
            Node prev = nodeToRemove.getPrevious();
            Node next = nodeToRemove.getNext();
            prev.setNext(next);
            next.setPrevious(prev);
        }

        size--;
        return removedValue;
    }

    private Node getNodeByIndex(int index) {
        Node current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrevious();
            }
        }
        return current;
    }

    public int size() {
        return size;
    }

    public void sort() {
        if (size < 2) return;
        first = mergeSort(first);
        last = first;
        while (last != null && last.getNext() != null) {
            last = last.getNext();
        }
    }

    private Node mergeSort(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.getNext();
        middle.setNext(null);

        Node left = mergeSort(head);
        Node right = mergeSort(nextOfMiddle);

        return sortAndMergeLists(left, right);
    }

    private Node getMiddle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow;
    }

    private Node sortAndMergeLists(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        Node result;
        if (a.getValue() <= b.getValue()) {
            result = a;
            result.setNext(sortAndMergeLists(a.getNext(), b));
        } else {
            result = b;
            result.setNext(sortAndMergeLists(a, b.getNext()));
        }
        result.getNext().setPrevious(result);
        result.setPrevious(null);
        return result;
    }

    public void forEach(IntConsumer action) {
        if (action == null) throw new NullPointerException();
        Node current = first;
        while (current != null) {
            action.toDo(current.getValue());
            current = current.getNext();
        }
    }

    public void printList() {
        forEach(val -> System.out.print(val + " "));
        System.out.println();
    }

    @FunctionalInterface
    public interface IntConsumer {
        void toDo(int v);
    }

    public static class Node {

        private Node previous;
        private Node next;
        private int value;

        public Node(int value) {
            this.value = value;
            this.previous = null;
            this.next = null;
        }

        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
        public Node getPrevious() { return previous; }
        public void setPrevious(Node previous) { this.previous = previous; }
        public Node getNext() { return next; }
        public void setNext(Node next) { this.next = next; }
    }
}
