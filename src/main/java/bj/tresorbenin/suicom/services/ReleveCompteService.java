package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.ReleveCompte;
import bj.tresorbenin.suicom.repositories.jpa.ReleveCompteRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleveCompteService extends AbstractBaseService<ReleveCompte> {

    @Getter
    @Autowired
    private ReleveCompteRepository repository;

    public ReleveCompteService() {
        super(ReleveCompte.class);
    }
}
