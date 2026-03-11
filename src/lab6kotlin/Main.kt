package lab6kotlin

import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

fun main() {
    println("1. Тестирование списка целых чисел (IntegerType):")
    val intType: UserType<Int> = IntegerType()
    val intList = UniversalLinkedList(intType, 50, 10, 30)
    intList.addFirst(5)
    intList.add(2, 20)

    print("Список до сортировки: ")
    intList.printList()

    intList.sort()
    print("Список после сортировки: ")
    intList.printList()

    println("Элемент по индексу 2: " + intList.get(2))

    val removed: Int = intList.remove(0)
    println("Удалили первый элемент ($removed)")
    print("Итоговый список Int: ")
    intList.printList()

    println("\n2. Тестирование списка строк (StringType):")
    val stringType: UserType<String> = StringType()
    val stringList = UniversalLinkedList(stringType,
        "Banana", "Apple", "Orange", "Pear"
    )

    print("Список до сортировки: ")
    stringList.printList()

    stringList.sort()
    print("Список после сортировки (лексикографически): ")
    stringList.printList()

    println("\n3. Тестирование методов readValue и parseValue:")

    val inputStr = "  42 "
    val parsedInt = intType.parseValue(inputStr)
    println("ParseValue (Int): '$inputStr' -> $parsedInt")

    val inputData = "Hello World"
    val bais = ByteArrayInputStream(inputData.toByteArray(StandardCharsets.UTF_8))
    val reader = InputStreamReader(bais)

    val readStr: String = stringType.readValue(reader)
    println("ReadValue (String): '$inputData' -> '$readStr'")
}