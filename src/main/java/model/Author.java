package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {
    private int id;
    private String name;
    private List<Book> books;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
        books = new ArrayList<Book>();
    }

    public Author() {
        books = new ArrayList<Book>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

        public List<Book> getBooks() {
        return books;
    }

    public void setBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

}
