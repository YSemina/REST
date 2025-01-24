package service;

import dto.AuthorDto;
import dto.BookDto;
import mapper.BookMapper;
import mapper.ModelMapper;
import model.Book;
import dao.BookDao;

import java.util.ArrayList;
import java.util.List;

public class BookService implements Service<BookDto> {
    private static final BookService INSTANCE = new BookService();

    public static BookService getInstance() {
        return INSTANCE;
    }

    private final BookDao bookDao = BookDao.getInstance();
    private final ModelMapper<Book, BookDto> bookMapper= new BookMapper();

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookDao.findAll();
        List<BookDto> bookDto = new ArrayList<>();
        for (Book book : books) {
            bookDto.add(bookMapper.toDto(book));
        }
        return bookDto;
    }

    @Override
    public BookDto findById(int id) {
        return bookDao.findById(id).
                map(bookMapper::toDto).
                orElseThrow(()->new RuntimeException("Не существует книги с id = " + id));
    }

    public List<BookDto> findByAuthor(int authorId) {
        List<Book> books = bookDao.findByAuthorId(authorId);
        List<BookDto> bookDto = new ArrayList<>();
        for (Book book : books) {
            bookDto.add(bookMapper.toDto(book));
        }
        return bookDto;
    }

    @Override
    public BookDto save(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        return bookMapper.toDto(bookDao.save(book));
    }

    @Override
    public boolean delete(int id) {
        return bookDao.delete(id);
    }

    @Override
    public boolean update(BookDto book) {
        return bookDao.update(bookMapper.toEntity(book));
    }

    private BookService() {}
}
