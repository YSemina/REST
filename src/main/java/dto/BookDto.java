package dto;

public class BookDto {
    private int id;
    private int authorId;
    private String bookTitle;
    private int quantity;

    public BookDto() {}

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getAuthorId(){
        return authorId;
    }

    public void setAuthorId(int authorId){
        this.authorId = authorId;
    }

    public String getBookTitle(){
        return bookTitle;
    }

    public void setBookTitle(String bookTitle){
        this.bookTitle = bookTitle;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
