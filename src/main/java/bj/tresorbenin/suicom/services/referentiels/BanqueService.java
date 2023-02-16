package bj.tresorbenin.suicom.services.referentiels;

import bj.tresorbenin.suicom.entities.referentiels.Banque;
import bj.tresorbenin.suicom.repositories.jpa.referentiels.BanqueRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanqueService extends AbstractBaseService<Banque> {

    @Autowired
    @Getter
    private BanqueRepository repository;

    public BanqueService() {
        super(Banque.class);
    }
}
