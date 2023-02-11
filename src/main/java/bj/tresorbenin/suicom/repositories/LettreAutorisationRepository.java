package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.LettreAutorisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LettreAutorisationRepository extends JpaRepository<LettreAutorisation, Long> {
}
