package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Long> {
}
