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
@SuppressWarnings("all")
public abstract class AbstractBaseService<E> {
    protected Class<?> entityClass;

    public AbstractBaseService(Class<?> klass) {
        this.entityClass = klass;
    }

    public abstract BaseRepository<E> getRepository();

    private long id(E entity) throws Exception {
        Class<? extends E> klass = (Class<? extends E>) entity.getClass();
        Method method = klass.getMethod("getId");
        return (Long) method.invoke(entity);
    }

    public E get(Object id) {
        long iid = Long.parseLong(id.toString());
        return getRepository().get(iid).orElse(null);
    }

    public E getById(Long id) {
        return get(id);
    }

    public E getByCode(String code) {
        return getRepository().findByCode(code).orElse(null);
    }

    public List<E> getAll() {
        return toList(getRepository().getAll());
    }

    protected List<E> toList(Set<E> set) {
        return new ArrayList<>(new HashSet<>(set));
    }

    @Transactional
    public E create(E entity) {
        return getRepository().save(entity);
    }

    @Transactional
    public E update(E entity) throws Exception {
        // Conserver les modifications de l'utilisateur en clonant
        Class<? extends E> klass = (Class<? extends E>) entity.getClass();
        Method cloneMethod = klass.getMethod("clone");
        E clone = (E) cloneMethod.invoke(entity);
        Class<? extends E> cloneKlass = (Class<? extends E>) clone.getClass();
        Method setIdMethod = cloneKlass.getMethod("setId", Long.class);
        setIdMethod.invoke(clone, (Object) null);
        // Ici, supprimons l'ancien de la base
        delete(entity);
        // Créer un nouvel enregistrement a partir de la copie
        return create(clone);
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
            delete(id(entity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}