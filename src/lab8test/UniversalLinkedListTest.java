package lab8test;

import lab2.UniversalLinkedList;
import lab3.IntegerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniversalLinkedListTest {

    private static final IntegerType INTEGER_TYPE = new IntegerType();
    
    // 1. СТРУКТУРНОЕ ТЕСТИРОВАНИЕ
    
    @Test
    @DisplayName("[СТРУКТУРНОЕ] Путь: size < 2 (пустой список)")
    public void testStructuralEmptyListPath() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE);
        assertDoesNotThrow(list::sort);
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("[СТРУКТУРНОЕ] Путь: size < 2 (один элемент)")
    public void testStructuralSingleElementPath() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 42);
        assertDoesNotThrow(list::sort);
        assertEquals(1, list.size());
        assertEquals(42, list.get(0));
    }

    @Test
    @DisplayName("[СТРУКТУРНОЕ] Условие: getMiddle (четное кол-во элементов)")
    public void testStructuralGetMiddleEvenPath() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 4, 3, 2, 1);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4});
    }

    @Test
    @DisplayName("[СТРУКТУРНОЕ] Условие: getMiddle (нечетное кол-во элементов)")
    public void testStructuralGetMiddleOddPath() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 3, 1, 4, 5, 2);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4, 5});
    }

    @Test
    @DisplayName("[СТРУКТУРНОЕ] Условие: sortAndMergeLists (ветви сравнения diff <= 0 и diff > 0)")
    public void testStructuralMergeBranches() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 2, 1, 4, 3);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4});
    }

    @Test
    @DisplayName("[СТРУКТУРНОЕ] Путь: обновление указателя last после сортировки")
    public void testStructuralLastPointerUpdatePath() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 3, 1, 2);
        list.sort();
        assertEquals(3, list.get(list.size() - 1));
        list.addLast(4);
        assertEquals(4, list.get(3));
    }
    
    // 2. ФУНКЦИОНАЛЬНОЕ ТЕСТИРОВАНИЕ

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] Исходный набор содержит одинаковые значения")
    public void testFunctionalAllIdentical() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 7, 7, 7, 7);
        list.sort();
        verifyOrder(list, new Integer[]{7, 7, 7, 7});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] Исходный набор неупорядочен")
    public void testFunctionalUnsorted() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 8, 3, 9, 1, 5);
        list.sort();
        verifyOrder(list, new Integer[]{1, 3, 5, 8, 9});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] Исходный набор упорядочен в прямом порядке")
    public void testFunctionalAlreadySorted() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 1, 2, 3, 4, 5);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4, 5});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] Исходный набор упорядочен в обратном порядке")
    public void testFunctionalReverseSorted() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 5, 4, 3, 2, 1);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4, 5});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] В наборе имеются повторяющиеся элементы")
    public void testFunctionalSomeDuplicates() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 1, 2, 1, 3);
        list.sort();
        verifyOrder(list, new Integer[]{1, 1, 2, 3});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] В наборе имеются несколько групп повторяющихся элементов")
    public void testFunctionalMultipleDuplicateGroups() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 2, 2, 1, 1, 3, 3);
        list.sort();
        verifyOrder(list, new Integer[]{1, 1, 2, 2, 3, 3});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] Экстремальное значение находится в середине набора")
    public void testFunctionalExtremeInMiddle() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 1, 2, 99, 3, 4);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4, 99});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] Экстремальное значение находится в начале набора")
    public void testFunctionalExtremeAtStart() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 99, 1, 2, 3, 4);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4, 99});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] Экстремальное значение находится в конце набора")
    public void testFunctionalExtremeAtEnd() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 1, 2, 3, 4, 99);
        list.sort();
        verifyOrder(list, new Integer[]{1, 2, 3, 4, 99});
    }

    @Test
    @DisplayName("[ФУНКЦИОНАЛЬНОЕ] В наборе несколько совпадающих экстремальных значений")
    public void testFunctionalMultipleExtremes() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 5, 1, 5, 1, 5);
        list.sort();
        verifyOrder(list, new Integer[]{1, 1, 5, 5, 5});
    }
    
    // 3. ТЕСТИРОВАНИЕ ПОЛУПРОЗРАЧНОГО ЯЩИКА

    @Test
    @DisplayName("[ПОЛУПРОЗРАЧНЫЙ ЯЩИК] Целостность внутренних указателей (prev/next) после сортировки")
    public void testGrayBoxInternalPointersIntegrity() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 3, 1, 2);
        list.sort();
        
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        
        UniversalLinkedList.Node<Integer> node1 = getNodeByReflection(list, 0);
        UniversalLinkedList.Node<Integer> node2 = getNodeByReflection(list, 1);
        UniversalLinkedList.Node<Integer> node3 = getNodeByReflection(list, 2);

        assertNull(node1.getPrevious(), "Первый узел не должен иметь previous");
        assertEquals(node2, node1.getNext());

        assertEquals(node1, node2.getPrevious());
        assertEquals(node3, node2.getNext());

        assertEquals(node2, node3.getPrevious());
        assertNull(node3.getNext(), "Последний узел не должен иметь next");
    }

    @Test
    @DisplayName("[ПОЛУПРОЗРАЧНЫЙ ЯЩИК] Корректность работы рекурсии mergeSort на глубоком стеке")
    public void testGrayBoxRecursionDepth() {
        Integer[] data = new Integer[1000];
        for (int i = 0; i < 1000; i++) data[i] = 1000 - i;

        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, data);
        assertDoesNotThrow(list::sort);
        assertEquals(1, list.get(0));
        assertEquals(1000, list.get(999));
    }
    
    // 4. ПРОВЕРКА ВНЕСЕНИЯ ДЕФЕКТОВ

    @Test
    @DisplayName("[ДЕФЕКТ 1] Проверка на нарушение логики компаратора")
    public void testDefectInjectionComparatorLogic() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 3, 2, 1);
        list.sort();
        assertEquals(1, list.get(0));
    }

    @Test
    @DisplayName("[ДЕФЕКТ 2] Проверка на ошибку в поиске середины (getMiddle)")
    public void testDefectInjectionGetMiddle() {
        UniversalLinkedList<Integer> evenList = new UniversalLinkedList<>(INTEGER_TYPE, 4, 3, 2, 1);
        evenList.sort();
        verifyOrder(evenList, new Integer[]{1, 2, 3, 4});
    }

    @Test
    @DisplayName("[ДЕФЕКТ 3] Проверка на потерю указателя last после сортировки")
    public void testDefectInjectionLastPointerLost() {
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(INTEGER_TYPE, 3, 1, 2);
        list.sort();
        assertEquals(3, list.get(list.size() - 1));
        list.addLast(4);
        assertEquals(4, list.get(list.size() - 1));
    }
    
    private void verifyOrder(UniversalLinkedList<Integer> list, Integer[] expected) {
        assertEquals(expected.length, list.size(), "Размер списка не совпадает с ожидаемым");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.get(i), "Несовпадение на индексе " + i);
        }
    }

    @SuppressWarnings("unchecked")
    private UniversalLinkedList.Node<Integer> getNodeByReflection(UniversalLinkedList<Integer> list, int index) {
        try {
            var method = UniversalLinkedList.class.getDeclaredMethod("getNodeByIndex", int.class);
            method.setAccessible(true);
            return (UniversalLinkedList.Node<Integer>) method.invoke(list, index);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка доступа к внутренней структуре через рефлексию", e);
        }
    }
}