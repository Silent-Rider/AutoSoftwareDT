package lab2;

import lab3.Comparator;
import lab3.UserType;

public class UniversalLinkedList<T> {

    private final UserType<T> userType;
    private Node<T> first;
    private Node<T> last;
    private int size;

    public UniversalLinkedList(UserType<T> userType) {
        this.userType = userType;
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    @SafeVarargs
    public UniversalLinkedList(UserType<T> userType, T ... values) {
        this(userType);
        for (T value: values) {
            addLast(value);
        }
    }

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
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

    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (last == null) {
            first = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrevious(last);
        }
        last = newNode;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = getNodeByIndex(index);
        return current.getValue();
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
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

    public void add(int index, T value) {
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

        Node<T> nextNode = getNodeByIndex(index);
        Node<T> prevNode = nextNode.getPrevious();
        Node<T> newNode = new Node<>(value);

        prevNode.setNext(newNode);
        newNode.setPrevious(prevNode);
        newNode.setNext(nextNode);
        nextNode.setPrevious(newNode);

        size++;
    }

    public T set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> nodeToSet = getNodeByIndex(index);
        T oldValue = nodeToSet.getValue();

        nodeToSet.setValue(value);
        return oldValue;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> nodeToRemove = getNodeByIndex(index);
        T removedValue = nodeToRemove.getValue();

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
            Node<T> prev = nodeToRemove.getPrevious();
            Node<T> next = nodeToRemove.getNext();
            prev.setNext(next);
            next.setPrevious(prev);
        }

        size--;
        return removedValue;
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

    private Node<T> mergeSort(Node<T> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node<T> middle = getMiddle(head);
        Node<T> nextOfMiddle = middle.getNext();
        middle.setNext(null);

        Node<T> left = mergeSort(head);
        Node<T> right = mergeSort(nextOfMiddle);

        return sortAndMergeLists(left, right);
    }

    private Node<T> getMiddle(Node<T> head) {
        Node<T> slow = head;
        Node<T> fast = head;

        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow;
    }

    private Node<T> sortAndMergeLists(Node<T> a, Node<T> b) {
        if (a == null) return b;
        if (b == null) return a;

        Node<T> result;
        Comparator comparator = userType.getTypeComparator();
        int diff = comparator.compare(a.getValue(), b.getValue());
        if (diff <= 0) {
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

    public void forEach(Consumer<T> action) {
        if (action == null) throw new NullPointerException();
        Node<T> current = first;
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
    public interface Consumer<T> {
        void toDo(T value);
    }

    public static class Node<T> {

        private Node<T> previous;
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
            this.previous = null;
            this.next = null;
        }

        public T getValue() { return value; }
        public void setValue(T value) { this.value = value; }
        public Node<T> getPrevious() { return previous; }
        public void setPrevious(Node<T> previous) { this.previous = previous; }
        public Node<T> getNext() { return next; }
        public void setNext(Node<T> next) { this.next = next; }
    }
}
