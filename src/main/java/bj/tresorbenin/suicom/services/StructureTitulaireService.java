package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.StructureTitulaire;
import bj.tresorbenin.suicom.repositories.StructureTitulaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

@Service
public class StructureTitulaireService implements CrudService<StructureTitulaire, Long>{


    @Autowired
    private StructureTitulaireRepository repo;

    @Override
    public List<StructureTitulaire> findAll() {
        return repo.findAll();
    }

    @Override
    public StructureTitulaire findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public StructureTitulaire create(StructureTitulaire entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public StructureTitulaire update(StructureTitulaire entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //StructureTitulaire banque = entity.clone(); banque.setId(null);
        // Ici, supprimons l'ancien de la base
        deleteById(entity.getId());
        // Créer un nouvel enregistrement a parttir du clone
        return create(entity);
    }

    @Override
    public void deleteById(Long id) {
        endDelete(findById(id));
    }

    @Override
    public void delete(StructureTitulaire entity) {
        endDelete(entity);
    }

    private void endDelete(StructureTitulaire entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(StructureTitulaire entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
