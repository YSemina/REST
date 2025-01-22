package db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private final static String URL = PropertiesDB.get("db.url");
    private final static String USER = PropertiesDB.get("db.username");
    private final static String PASSWORD = PropertiesDB.get("db.password");
    private final static String DRIVER = PropertiesDB.get("db.driver");

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в ConnectionManager!", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Ошибка с драйвером", e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Ошибка из-за DeclaredConstructor", e);
        }
    }

    private ConnectionManager() {}
}
