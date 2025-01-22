package db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerTest {
@Test
public void connection() {
    try(Connection connection = ConnectionManager.getConnection()){
        System.out.println(connection.getClass());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

}
