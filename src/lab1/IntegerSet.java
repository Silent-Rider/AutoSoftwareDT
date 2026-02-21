package lab1;

import java.util.Arrays;

public class IntegerSet {

    private int[] numbers = new int[0];

    public IntegerSet() {}

    public IntegerSet(IntegerSet integerSet) {
        this(integerSet.numbers);
    }

    public IntegerSet(int... numbers) {
        addAll(numbers);
    }

    public int size() {
        return numbers.length;
    }

    public boolean isEmpty() {
        return numbers.length == 0;
    }

    public boolean contains(int number) {
        for (int i : numbers) {
            if (i == number) return true;
        }
        return false;
    }

    public boolean add(int number) {
        if (contains(number)) return false;
        int[] temp = numbers;
        numbers = new int[numbers.length + 1];
        for (int i = 0; i <= temp.length; i++) {
            if (i == temp.length) {
                numbers[i] = number;
            } else {
                numbers[i] = temp[i];
            }
        }
        return true;
    }

    public boolean remove(int number) {
        if (!contains(number)) return false;
        int[] temp = numbers;
        numbers = new int[numbers.length - 1];
        for (int i = 0, j = 0; i < temp.length; i++, j++) {
            if (temp[i] != number) {
                numbers[j] = temp[i];
            } else {
                j--;
            }
        }
        return true;
    }

    public boolean containsAll(int... numbers) {
        for (int number: numbers) {
            if (!contains(number)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsAll(IntegerSet integerSet) {
        return containsAll(integerSet.numbers);
    }

    public boolean addAll(int... numbers) {
        if (containsAll(numbers)) return false;
        for (int number: numbers) {
            add(number);
        }
        return true;
    }

    public boolean addAll(IntegerSet integerSet) {
        return addAll(integerSet.numbers);
    }

    public boolean removeAll(int... numbers) {
        boolean removed = false;
        for (int number: numbers) {
            if (remove(number)) {
                removed = true;
            }
        }
        return removed;
    }

    public boolean removeAll(IntegerSet integerSet) {
        return removeAll(integerSet.numbers);
    }

    public boolean retainAll(IntegerSet integerSet) {
        boolean changed = false;
        for (int number: numbers) {
            if (!integerSet.contains(number)) {
                changed = remove(number);
            }
        }
        return changed;
    }

    public boolean retainAll(int... numbers) {
        return retainAll(new IntegerSet(numbers));
    }

    @Override
    public String toString() {
        return Arrays.toString(numbers);
    }
}
