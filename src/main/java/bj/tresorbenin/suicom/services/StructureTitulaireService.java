package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.StructureTitulaire;
import bj.tresorbenin.suicom.repositories.jpa.StructureTitulaireRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StructureTitulaireService extends AbstractBaseService<StructureTitulaire> {

    @Getter
    @Autowired
    private StructureTitulaireRepository repository;

    public StructureTitulaireService() {
        super(StructureTitulaire.class);
    }
}
