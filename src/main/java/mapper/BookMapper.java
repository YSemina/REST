package mapper;

import dto.BookDto;
import model.Book;

public class BookMapper implements ModelMapper<Book, BookDto>{

    @Override
    public BookDto toDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( book.getId() );
        bookDto.setAuthorId( book.getAuthorId());
        bookDto.setBookTitle( book.getBookTitle() );
        bookDto.setQuantity( book.getQuantity() );

        return bookDto;
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDto.getId() );
        book.setAuthorId( bookDto.getAuthorId());
        book.setBookTitle( bookDto.getBookTitle() );
        book.setQuantity( bookDto.getQuantity() );

        return book;
    }
}
