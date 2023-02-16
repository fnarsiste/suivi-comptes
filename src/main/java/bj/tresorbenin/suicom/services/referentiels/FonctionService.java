package bj.tresorbenin.suicom.services.referentiels;

import bj.tresorbenin.suicom.entities.referentiels.Fonction;
import bj.tresorbenin.suicom.repositories.jpa.referentiels.FonctionRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FonctionService extends AbstractBaseService<Fonction> {

    @Autowired
    @Getter
    private FonctionRepository repository;

    public FonctionService() {
        super(Fonction.class);
    }
}
