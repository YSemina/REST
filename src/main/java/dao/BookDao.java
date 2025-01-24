package dao;

import dao.request.RequestBook;
import db.ConnectionManager;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao implements Dao<Book> {
    private final static BookDao INSTANCE = new BookDao();

    public static BookDao getInstance() {
        return INSTANCE;
    }
    @Override
    public List<Book> findAll() {
        String SQL = RequestBook.FIND_ALL;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при чтении книг", e);
        }
    }

    public List<Book> findByAuthorId(int authorId) {
        String SQL = RequestBook.FIND_BY_AUTHOR_ID;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске книги по id автора", e);
        }
    }
    @Override
    public Optional<Book> findById(int id) {
        String SQL = RequestBook.FIND_BY_ID;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = buildBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске книги по id", e);
        }
    }

    @Override
    public Book save(Book book) {
        if (!bookValidate(book)) {
            return null;
        }
        String SQL = RequestBook.BOOK_ADD;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, book.getAuthorId());
            statement.setString(2, book.getBookTitle());
            statement.setInt(3, book.getQuantity());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                book.setId(keys.getInt("id"));
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении книги", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String SQL = RequestBook.DELETE_BY_ID;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении книги по id", e);
        }
    }

    //обновление кол-ва книг по названию и id автора
    @Override
    public boolean update(Book book) {
        String SQL = RequestBook.UPDATE;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, book.getQuantity());
            statement.setString(2, book.getBookTitle());
            statement.setInt(3, book.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении книги", e);
        }
    }

    private boolean bookValidate(Book book) {
        if (book.getBookTitle() == null) return false;
        String newTitle = modificationTitle(book.getBookTitle());
        List<Book> allBooks = findAll();
        for (Book books : allBooks) {
            //если строки с названиями совпадают, не добавляем
            if (newTitle.equalsIgnoreCase(books.getBookTitle())) {
                return false;
            }
            //если название содержится в БД и id авторов совпадают, не добавляем
            if (books.getBookTitle().toLowerCase().contains(newTitle.toLowerCase())
            && (books.getAuthorId()==book.getAuthorId())) {
                return false;
            }
        }
        return true;
    }

    private String modificationTitle(String title) {
        String[] words = title.split("\\s*(\\s|,|!|\\.)\\s*");
        String newTitle = words[0];
        for (int i = 1; i < words.length; i++) {
            newTitle = String.join(" ", newTitle, words[i]);
        }
        return newTitle;
    }

    private Book buildBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getInt("id"),
                resultSet.getInt("author_id"),
                resultSet.getString("title"),
                resultSet.getInt("quantity"));
    }

    private BookDao() {}
}
