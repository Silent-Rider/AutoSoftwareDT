package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class DoubleType implements UserType<Double> {

    @Override
    public String typeName() {
        return Double.class.getTypeName();
    }

    @Override
    public Double create() {
        return 0.0;
    }

    @Override
    public Double clone(Double d) {
        return d;
    }

    @Override
    public Double readValue(InputStreamReader in) throws IOException {
        try (BufferedReader br = new BufferedReader(in)) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                return 0.0;
            }
            return Double.parseDouble(line.trim());
        }
    }

    @Override
    public Double parseValue(String doubleString) {
        if (doubleString == null || doubleString.trim().isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(doubleString.trim());
    }

    @Override
    public Comparator<Double> getTypeComparator() {
        return Double::compareTo;
    }
}
