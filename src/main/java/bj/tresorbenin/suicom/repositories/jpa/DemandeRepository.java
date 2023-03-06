package bj.tresorbenin.suicom.repositories.jpa;


import bj.tresorbenin.suicom.entities.Demande;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Set;

public interface DemandeRepository extends BaseRepository<Demande> {
    @Query("FROM #{#entityName} WHERE dateDemande BETWEEN ?1 AND ?2  AND dateCessation = 'Infinity' ORDER BY id DESC")
    Set<Demande> getByPeriod(Date from, Date to);
}
