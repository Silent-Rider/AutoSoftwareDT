package lab3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public interface UserType<T> {
    public String typeName();
    public T create();
    public T clone(T o);
    public T readValue(InputStreamReader in) throws IOException;
    public T parseValue(String string);
    public Comparator<T> getTypeComparator();
}
