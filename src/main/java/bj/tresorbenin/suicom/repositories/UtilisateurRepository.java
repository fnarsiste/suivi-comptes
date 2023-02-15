package bj.tresorbenin.suicom.repositories;

import bj.tresorbenin.suicom.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
}
