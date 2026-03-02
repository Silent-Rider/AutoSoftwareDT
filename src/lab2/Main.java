package lab2;

public class Main {

    public static void main(String[] args) {
        IntegerLinkedList list = new IntegerLinkedList();

        list.addLast(2);
        list.addLast(8);
        list.addLast(1);
        list.addFirst(5);

        System.out.print("Исходный: ");
        list.printList();

        list.add(1, 10);
        System.out.print("После вставки 10 на индекс 1: ");
        list.printList();

        System.out.println("Элемент на индексе 2: " + list.get(2));

        list.remove(0);
        System.out.print("После удаления индекса 0: ");
        list.printList();

        list.set(0, 7);
        System.out.print("После установки нового значения элементу под индексом 0: ");
        list.printList();

        list.sort();
        System.out.print("После сортировки: ");
        list.printList();

        System.out.print("Обход forEach: ");
        list.forEach(val -> System.out.print("[" + val + "] "));
    }
}
