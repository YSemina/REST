package service;

import java.util.List;

public interface Service<D> {
    List<D> findAll();
    D findById(int id);
    D save(D Dto);
    boolean delete(int id);
    boolean update(D model);
}
