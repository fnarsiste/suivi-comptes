package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.Fonction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonctionRepository extends JpaRepository<Fonction, Long> {
}
