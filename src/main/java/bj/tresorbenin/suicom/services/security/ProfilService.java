package bj.tresorbenin.suicom.services.security;

import bj.tresorbenin.suicom.entities.administration.Profil;
import bj.tresorbenin.suicom.repositories.jpa.auth.ProfilRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilService extends AbstractBaseService<Profil> {
    @Autowired
    @Getter
    private ProfilRepository repository;

    public ProfilService() {
        super(Profil.class);
    }
}
