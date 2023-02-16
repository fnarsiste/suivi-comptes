package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Banque;
import bj.tresorbenin.suicom.repositories.jpa.BanqueRepository;
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

    public Banque update__(Banque entity) {
        // Conserver les modifications de l'utilisateur en clonant
        Banque banque = entity.clone();
        banque.setId(null);
        // Ici, supprimons l'ancien de la base
        delete(entity.getId());
        // Créer un nouvel enregistrement a parttir du clone
        return create(banque);
    }
}
