package bj.tresorbenin.suicom.services.referentiels;

import bj.tresorbenin.suicom.entities.referentiels.Statut;
import bj.tresorbenin.suicom.repositories.jpa.referentiels.StatutRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class StatutService extends AbstractBaseService<Statut> {

    @Autowired
    @Getter
    private StatutRepository repository;

    public StatutService() {
        super(Statut.class);
    }
}
