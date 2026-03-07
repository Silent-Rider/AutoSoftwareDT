package lab4;

import lab3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserFactory {

    private final Map<String, UserType<?>> registry;

    public UserFactory() {
        this.registry = new HashMap<>();
        register(new IntegerType());
        register(new StringType());
        register(new DoubleType());
    }

    private void register(UserType<?> type) {
        registry.put(type.typeName(), type);
    }

    public ArrayList<String> getTypeNameList() {
        return new ArrayList<>(registry.keySet());
    }

    public UserType<?> getBuilderByName(String name) {
        UserType<?> type = registry.get(name);
        if (type == null) {
            throw new IllegalArgumentException("Unknown type: " + name);
        }
        return type;
    }
}
