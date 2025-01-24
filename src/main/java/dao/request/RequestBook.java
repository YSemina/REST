package dao.request;

public final class RequestBook {
    public static final String FIND_ALL = """
            SELECT id, author_id, title, quantity FROM books
            ORDER BY author_id, title
            """;
    public static final String FIND_BY_AUTHOR_ID = """
            SELECT books.id, author_id, title, quantity
            FROM books
            JOIN authors ON books.author_id = authors.id
            WHERE author_id=?
            """;
    public static final String FIND_BY_ID = """
            SELECT id, author_id, title, quantity
            FROM books
            WHERE id=?
            """;
    public static final String DELETE_BY_ID = """
            DELETE FROM books
            WHERE id=?
            """;
    public static final String BOOK_ADD = """
            INSERT INTO books (author_id, title, quantity)
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE = """
            UPDATE books
            SET quantity=?, title=?
            WHERE id=?
            """;

    private RequestBook() {
    }
}
