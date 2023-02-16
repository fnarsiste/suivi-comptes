package bj.tresorbenin.suicom.services.security;

import bj.tresorbenin.suicom.entities.auth.Profil;
import bj.tresorbenin.suicom.repositories.jpa.auth.ProfilRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfilService extends AbstractBaseService<Profil> {
    @Autowired
    @Getter
    private ProfilRepository repository;

    public ProfilService() {
        super(Profil.class);
    }
}
