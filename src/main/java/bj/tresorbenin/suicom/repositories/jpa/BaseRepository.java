package bj.tresorbenin.suicom.repositories.jpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Interface de base pour les signature des fonctions DAO communes a tous les
 * repositories<br>
 * NB : L'annotation @NoRepositoryBean s'utilise dans le cas ou la classe fille
 * implémentant la JpaRepository ne définit pas les types paramétrisés
 *
 * @param <T> Represents the entity to be injected
 * @author nfassinou
 * @since July 2022
 */
@NoRepositoryBean
@Transactional(readOnly = true)
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    /**
     * Returns the record identified by the given ID
     * @param id ID of record to retrieve
     * @return Optional&lt;T&gt; record identified
     */
    @Query("FROM #{#entityName} WHERE id = ?1 AND dateCessation = 'Infinity'")
    Optional<T> get(Long id);

    /**
     * Returns the record identified by the given code
     * @param code Code of record to retrieve
     * @return Optional&lt;T&gt; record identified
     */
    @Query("FROM #{#entityName} WHERE code = ?1 AND dateCessation = 'Infinity'")
    Optional<T> getByCode(String code);



    /**
     * Returns the list of not dead records in the linked table.
     * @return Set&lt;T&gt; records
     */
    @Query("FROM #{#entityName} WHERE dateCessation = 'Infinity' ORDER BY id DESC")
    Set<T> getAll();

    /**
     * Returns the sorted list of not dead records in the linked table.
     * @param sort The sort order
     * @return Set&lt;T&gt; records
     */
    @Query("FROM #{#entityName} WHERE dateCessation = 'Infinity'")
    Set<T> getAll(Sort sort);

    /**
     * Returns the set of records identified by the given edited
     * @param editedBy Code of record to retrieve
     * @return Optional&lt;T&gt; record identified
     */
    @Query("FROM #{#entityName} WHERE modifierPar = ?1 AND dateCessation = 'Infinity'")
    @SuppressWarnings("unused")
    Set<T> findByModifierPar(String editedBy);

    /**
     * <b>Methode utilitaire de cessation des enregistrements.</b><br>
     * Notes : le CAST() utilisé est pour convertir le now() en Date comprehensible par la couche Hibernate<br>
     * Il est mis pour éviter une exception de conversion d'<b>Object en Date</b>
     * @param id ID de la ligne a supprimer
     */
    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} SET dateCessation = CAST(now() as java.util.Date) WHERE id = ?1")
    void unlink(Long id);
}
