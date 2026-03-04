package lab3;

import lab1.IntegerSet;
import lab2.UniversalLinkedList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        System.out.println("1. Тестирование списка целых чисел (IntegerType):");
        UserType<Integer> intType = new IntegerType();
        UniversalLinkedList<Integer> intList = new UniversalLinkedList<>(intType, 50, 10, 30);
        intList.addFirst(5);
        intList.add(2, 20);

        System.out.print("Список до сортировки: ");
        intList.printList();

        intList.sort();
        System.out.print("Список после сортировки: ");
        intList.printList();

        System.out.println("Элемент по индексу 2: " + intList.get(2));

        int removed = intList.remove(0);
        System.out.println("Удалили первый элемент (" + removed + ")");
        System.out.print("Итоговый список Int: ");
        intList.printList();

        System.out.println("\n2. Тестирование списка строк (StringType):");
        UserType<String> stringType = new StringType();
        UniversalLinkedList<String> stringList = new UniversalLinkedList<>(stringType,
                "Banana","Apple","Orange","Pear");

        System.out.print("Список до сортировки: ");
        stringList.printList();

        stringList.sort();
        System.out.print("Список после сортировки (лексикографически): ");
        stringList.printList();

        System.out.println("\n3. Тестирование списка множеств (IntegerSetType):");
        UserType<IntegerSet> setType = new IntegerSetType();
        UniversalLinkedList<IntegerSet> setList = new UniversalLinkedList<>(setType);

        IntegerSet s1 = new IntegerSet(1, 5, 3);
        IntegerSet s2 = new IntegerSet(10, 20);
        IntegerSet s3 = new IntegerSet(1, 2, 3, 4, 5, 6);
        IntegerSet s4 = new IntegerSet();

        setList.addLast(s1);
        setList.addLast(s2);
        setList.addLast(s3);
        setList.addLast(s4);

        System.out.println("Список до сортировки (по размеру множества):");
        setList.printList();

        setList.sort();
        System.out.println("Список после сортировки (возрастание размера):");
        setList.printList();

        System.out.println("\n4. Тестирование методов readValue и parseValue:");

        String inputStr = "  42 ";
        Integer parsedInt = intType.parseValue(inputStr);
        System.out.println("ParseValue (Int): '" + inputStr + "' -> " + parsedInt);

        String inputData = "Hello World\n";
        ByteArrayInputStream bais = new ByteArrayInputStream(inputData.getBytes(StandardCharsets.UTF_8));
        InputStreamReader reader = new InputStreamReader(bais);

        try {
            String readStr = stringType.readValue(reader);
            System.out.println("ReadValue (String): '" + readStr + "'");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении из потока");
        }

        String setStr = "[1, 2, 3, 100]";
        IntegerSet parsedSet = setType.parseValue(setStr);
        System.out.println("ParseValue (IntegerSet): '" + setStr + "' -> " + parsedSet);
    }
}
