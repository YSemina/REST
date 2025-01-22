package db;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesDB {
    private static final Properties PROPERTIES = new Properties();
    static
    {
        loadProperties();
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesDB.class.getClassLoader().getResourceAsStream("db.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в PropertiesDB", e);
        } ;
    }

    public static String get (String key){
        return PROPERTIES.getProperty(key);
    }

    private PropertiesDB() {}
}
