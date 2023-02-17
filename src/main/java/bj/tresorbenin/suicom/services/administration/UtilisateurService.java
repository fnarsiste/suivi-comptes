package bj.tresorbenin.suicom.services.administration;

import bj.tresorbenin.suicom.entities.administration.Utilisateur;
import bj.tresorbenin.suicom.repositories.jpa.auth.UtilisateurRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService extends AbstractBaseService<Utilisateur> {
    @Autowired
    @Getter
    private UtilisateurRepository repository;

    public UtilisateurService() {
        super(Utilisateur.class);
    }
}
