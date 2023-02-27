package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Fiche;
import bj.tresorbenin.suicom.repositories.jpa.BaseRepository;
import bj.tresorbenin.suicom.repositories.jpa.DemandeRepository;
import bj.tresorbenin.suicom.repositories.jpa.FicheRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FicheService extends AbstractBaseService<Fiche>{

    @Autowired
    @Getter
    private FicheRepository repository;
    public FicheService() {
        super(Fiche.class);
    }

    @Override
    public BaseRepository<Fiche> getRepository() {
        return null;
    }
}
