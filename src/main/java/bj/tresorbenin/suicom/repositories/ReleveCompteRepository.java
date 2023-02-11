package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.ReleveCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleveCompteRepository extends JpaRepository<ReleveCompte, Long> {
}
