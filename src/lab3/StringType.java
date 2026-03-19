package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class StringType implements UserType<String> {

    @Override
    public String typeName() {
        return String.class.getTypeName();
    }

    @Override
    public String create() {
        return "";
    }

    @Override
    public String clone(String s) {
        return s;
    }

    @Override
    public String readValue(InputStreamReader in) throws IOException {
        try (BufferedReader br = new BufferedReader(in)) {
            String line = br.readLine();
            return line == null ? "" : line.trim();
        }
    }

    @Override
    public String parseValue(String stringVal) {
        return stringVal != null ? stringVal : "";
    }

    @Override
    public Comparator<String> getTypeComparator() {
        return String::compareTo;
    }
}
