package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> findAll();
    Optional<T> findById(int id);
    T save (T model);
    boolean delete (int id);
    boolean update (T model);

}
