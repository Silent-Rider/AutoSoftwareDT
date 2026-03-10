package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IntegerType implements UserType<Integer> {

    @Override
    public String typeName() {
        return Integer.class.getTypeName();
    }

    @Override
    public Integer create() {
        return 0;
    }

    @Override
    public Integer clone(Integer i) {
        return i;
    }

    @Override
    public Integer readValue(InputStreamReader in) throws IOException {
        try (BufferedReader br = new BufferedReader(in)) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                return 0;
            }
            return Integer.parseInt(line.trim());
        }
    }

    @Override
    public Integer parseValue(String integerString) {
        if (integerString == null || integerString.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(integerString.trim());
    }

    @Override
    public Comparator getTypeComparator() {
        return (o1, o2) -> {
            if (!(o1 instanceof Integer i1) || !(o2 instanceof Integer i2)) {
                throw new ClassCastException();
            }
            return i1.compareTo(i2);
        };
    }
}
