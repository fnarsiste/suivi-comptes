package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Demande;
import bj.tresorbenin.suicom.repositories.jpa.DemandeRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemandeService extends AbstractBaseService<Demande> {

    @Autowired
    @Getter
    private DemandeRepository repository;

    public DemandeService() {
        super(Demande.class);
    }
}
