package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
}
