package lab1;

import java.io.*;
import java.util.Arrays;

public class IntegerSet implements Serializable {

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

    public void saveToBinary(String filename) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
            output.writeObject(this);
        }
    }

    public static IntegerSet loadFromBinary(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))) {
            return (IntegerSet) input.readObject();
        }
    }

    public void saveToText(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(this);
        }
    }

    public static IntegerSet loadFromText(String filename) throws IOException {
        String stringSet;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            stringSet = reader.readLine();
            if (stringSet == null) throw new IOException();
        }
        IntegerSet integerSet = new IntegerSet();
        String content = stringSet.replaceAll("[\\[\\]\\s]", "");
        if (!content.isEmpty()) {
            String[] numbers = content.split(",");
            for (String part : numbers) {
                try {
                    integerSet.add(Integer.parseInt(part));
                } catch (NumberFormatException ignored) {}
            }
        }
        return integerSet;
    }

    @Override
    public String toString() {
        return Arrays.toString(numbers);
    }
}
