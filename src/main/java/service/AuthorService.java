package service;

import model.Author;
import dao.AuthorDao;
import mapper.ModelMapper;
import mapper.AuthorMapper;
import dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;

public class AuthorService implements Service<AuthorDto> {
    private static final AuthorService INSTANCE = new AuthorService();

    public static AuthorService getInstance() {
        return INSTANCE;
    }
    private final AuthorDao authorDao = AuthorDao.getInstance();
    private final ModelMapper<Author,AuthorDto> authorMapper= new AuthorMapper();

    @Override
    public List<AuthorDto> findAll() {
        List<Author> authors = authorDao.findAll();
        List<AuthorDto> authorDto = new ArrayList<>();
        for (Author author : authors) {
            authorDto.add(authorMapper.toDto(author));
        }
        return authorDto;
    }

    @Override
    public AuthorDto findById(int id) throws RuntimeException {
        return authorDao.findById(id).
                map(authorMapper::toDto).
                orElseThrow(()->new RuntimeException("Не существует автора с id = " + id));
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        return authorMapper.toDto(authorDao.save(author));
    }

    @Override
    public boolean delete(int id) {
        return authorDao.delete(id);
    }

    @Override
    public boolean update(AuthorDto author){
        return authorDao.update(authorMapper.toEntity(author));
    }

    public int getAuthorId(String authorName){
        return authorDao.getAuthorId(authorName);
    }

    private AuthorService() {}
}
