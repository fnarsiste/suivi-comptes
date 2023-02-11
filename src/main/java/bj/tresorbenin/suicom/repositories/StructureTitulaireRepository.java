package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.StructureTitulaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureTitulaireRepository extends JpaRepository<StructureTitulaire, Long> {
}
