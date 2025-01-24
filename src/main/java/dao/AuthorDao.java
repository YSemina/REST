package dao;

import dao.request.RequestAuthor;
import db.ConnectionManager;
import model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDao implements Dao<Author> {

    private final static AuthorDao INSTANCE = new AuthorDao();

    public static AuthorDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Author> findAll() {
        String SQL = RequestAuthor.FIND_ALL;

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                authors.add(buildAuthor(resultSet));
            }
            return authors;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при чтении авторов", e);
        }
    }

    @Override
    public Optional<Author> findById(int id) {
        String SQL = RequestAuthor.FIND_BY_ID;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            Author author = null;
            if (resultSet.next()) {
                author = buildAuthor(resultSet);
            }
            return Optional.ofNullable(author);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске автора по id", e);
        }
    }

    @Override
    public Author save(Author author) {
        if (!authorValidate(author)) {
            return null;
        }
        String SQL = RequestAuthor.AUTHOR_ADD;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                author.setId(keys.getInt("id"));
            }
            return author;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении автора", e);
        }
    }

    @Override
    public boolean delete(int id) {
        String SQL = RequestAuthor.DELETE_BY_ID;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении автора по id", e);
        }
    }

    @Override
    public boolean update(Author author) {
        String SQL = RequestAuthor.UPDATE;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, author.getName());
            statement.setInt(2, author.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении автора", e);
        }
    }

    public int getAuthorId(String authorName) {
        String SQL = RequestAuthor.FIND_ID;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, authorName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
            return  resultSet.getInt("id");
            }
            else return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске id автора", e);
        }
    }

    private String getLastName(String name) {
        //разделяю ФИО (если несколько слов введено)
        String[] words = name.split("\\s*(\\s|,|!|\\.)\\s*");
        String lastName = null;
        int max = -1;
        //выбираю самое длинное слово (фамилию), т. к. предполагается, что вводятся инициалы и фамилия
        for (String word : words) {
            if (word.length() > max) {
                max = word.length();
                lastName = word;
            }
        }
        return lastName;
    }

    private boolean authorValidate(Author author) {
        if (author.getName() == null) return false;
        String lastName = getLastName(author.getName());
        List<Author> allAuthors = findAll();
        for (Author authors : allAuthors) {
            //если строки с именами совпадают, не добавляем
            if (author.getName().trim().equalsIgnoreCase(authors.getName())) {
                return false;
            }
            //если фамилия содержится в БД, не добавляем
            if (authors.getName().toLowerCase().contains(lastName.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    private Author buildAuthor(ResultSet resultSet) throws SQLException {
        return new Author(resultSet.getInt("id"), resultSet.getString("name"));
    }

    private AuthorDao() {
    }
}
