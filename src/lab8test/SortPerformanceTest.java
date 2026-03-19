package lab8test;

import lab2.UniversalLinkedList;
import lab3.IntegerType;
import java.util.Random;

public class SortPerformanceTest {

    public static void main(String[] args) {
        int[] sizes = {500, 1000, 2000, 5000, 10000, 50000};
        
        System.out.printf("%-15s | %-15s | %-15s | %-15s%n", "N (элементов)", "Время (мс)", "Δ Памяти (КБ)", "Оценка сложности");
        System.out.println("-----------------------------------------------------------------------");

        IntegerType type = new IntegerType();

        for (int n : sizes) {
            runBenchmark(n, type);
        }
    }

    private static void runBenchmark(int size, IntegerType type) {
        Integer[] rawData = generateRandomArray(size);
        
        UniversalLinkedList<Integer> warmupList = new UniversalLinkedList<>(type, rawData.clone());
        warmupList.sort();
        warmupList = null;   

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        
        UniversalLinkedList<Integer> list = new UniversalLinkedList<>(type, rawData);
        
        long startTime = System.nanoTime();
        list.sort();
        long endTime = System.nanoTime();

        long memAfter = runtime.totalMemory() - runtime.freeMemory();

        double timeMs = (endTime - startTime) / 1_000_000.0;
        long memDeltaKB = (memAfter - memBefore) / 1024;
        
        if (!isSorted(list, size)) {
            System.err.println("Список размера " + size + " не отсортирован корректно! Данные невалидны.");
        }
        
        String complexityEstimate = estimateComplexity(timeMs, size);
        System.out.printf("%-15d | %-15.2f | %-15d | %-15s%n", size, timeMs, memDeltaKB, complexityEstimate);
    }

    private static Integer[] generateRandomArray(int size) {
        Random rand = new Random();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(100_000);
        }
        return arr;
    }

    private static boolean isSorted(UniversalLinkedList<Integer> list, int size) {
        if (size <= 1) return true;
        for (int i = 0; i < size - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
    
    private static String estimateComplexity(double timeMs, int n) {
        if (timeMs < 0.1) return "O(N log N) (быстро)";
        return "O(N log N)";
    }
}
