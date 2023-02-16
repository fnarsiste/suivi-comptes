package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.repositories.jpa.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe abstraite implementant les services génériques pour la persistence des données.
 *
 * @param <E>
 * @author <a href="mailto:fnarsiste@gmail.com">Nars Sixte</a>
 * @date February 16th, 2023
 */
public abstract class AbstractBaseService<E> {
    protected Class<?> entityClass;

    public AbstractBaseService(Class<?> klass) {
        this.entityClass = klass;
    }

    public abstract BaseRepository<E> getRepository();

    public abstract E update(E entity);

    public E get(Object id) {
        long iid = Long.parseLong(id.toString());
        return getRepository().findById(iid).orElse(null);
    }

    public E getById(Long id) {
        return get(id);
    }

    public E getByCode(String code) {
        return getRepository().findByCode(code).orElse(null);
    }

    public List<E> getAll() {
        return new ArrayList<>(new HashSet<>(getRepository().getAll()));
    }

    @Transactional
    public E create(E entity) {
        return getRepository().save(entity);
    }

    @Transactional
    public void saveAll(Set<E> entities) {
        getRepository().saveAll(entities);
    }

    @Transactional
    public void delete(Long id) {
        getRepository().unlink(id);
    }

    @Transactional
    public void delete(E entity) {
        try {
            Class<?> klass = entity.getClass();
            Method method = klass.getDeclaredMethod("getId");
            Long id = (Long) method.invoke(klass);
            delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}