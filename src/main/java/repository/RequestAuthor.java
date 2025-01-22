package repository;

public final class RequestAuthor {

    public static final String FIND_ALL = """
            SELECT id, name FROM authors
            """;
    public static final String FIND_BY_ID = """
            SELECT name FROM authors
            WHERE id=?
            """;
    public static final String DELETE_BY_ID = """
            DELETE FROM authors
            WHERE id=?
            """;
    public static final String AUTHOR_ADD = """
            INSERT INTO authors (name)
            VALUES (?)
            """;

    public static final String UPDATE = """
            UPDATE authors
            SET name=?
            WHERE id=?
            """;
    public static final String FIND_ID = """
            SELECT id
            FROM authors
            WHERE name=?
            """;

    private RequestAuthor() {
    }
}
