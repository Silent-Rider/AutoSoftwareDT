package lab3;

import lab1.IntegerSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class IntegerSetType implements UserType<IntegerSet> {

    @Override
    public String typeName() {
        return IntegerSet.class.getTypeName();
    }

    @Override
    public IntegerSet create() {
        return new IntegerSet();
    }

    @Override
    public IntegerSet clone(IntegerSet initialSet) {
        return new IntegerSet(initialSet);
    }

    @Override
    public IntegerSet readValue(InputStreamReader in) throws IOException{
        try (BufferedReader reader = new BufferedReader(in)) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                return new IntegerSet();
            }
            return parseValue(line);
        }
    }

    @Override
    public IntegerSet parseValue(String stringSet) {
        return IntegerSet.parseValue(stringSet);
    }

    @Override
    public Comparator<IntegerSet> getTypeComparator() {
        return Comparator.comparingInt(IntegerSet::size);
    }
}
