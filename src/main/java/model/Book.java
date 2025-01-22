package model;

import java.util.Objects;

public class Book {
    private int id;
    private int authorId;
    private String bookTitle;
    private int quantity;
    private Author author;

    public Book(int id, int authorId, String bookTitle, int quantity) {
        this.id = id;
        this.authorId = authorId;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
    }

    public Book() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return authorId == book.authorId && quantity == book.quantity && Objects.equals(bookTitle, book.bookTitle)
                && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, bookTitle, quantity, author);
    }
}
