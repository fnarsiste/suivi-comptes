package bj.tresorbenin.suicom.services;


import bj.tresorbenin.suicom.entities.LettreAutorisation;
import bj.tresorbenin.suicom.repositories.jpa.LettreAutorisationRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LettreAutorisationService extends AbstractBaseService<LettreAutorisation> {

    @Autowired
    @Getter
    private LettreAutorisationRepository repository;

    public LettreAutorisationService() {
        super(LettreAutorisation.class);
    }
}
