package bj.tresorbenin.suicom.services.administration;

import bj.tresorbenin.suicom.entities.administration.ProfilUtilisateur;
import bj.tresorbenin.suicom.repositories.jpa.auth.ProfilUtilisateurRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProfilUtilisateurService extends AbstractBaseService<ProfilUtilisateur> {
    @Autowired
    @Getter
    ProfilUtilisateurRepository repository;

    public ProfilUtilisateurService() {
        super(ProfilUtilisateur.class);
    }
}
