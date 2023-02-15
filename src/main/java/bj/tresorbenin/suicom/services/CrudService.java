package bj.tresorbenin.suicom.services;

import java.util.List;

public interface CrudService <T,ID> {

    List<T> findAll();
    
    T findById(ID id);

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);

    void beforeCreate(T entity);
}
