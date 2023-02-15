package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Profil;
import bj.tresorbenin.suicom.repositories.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

public class ProfilService implements CrudService<Profil,Long>{
    @Autowired
    private ProfilRepository repo;

    @Override
    public List<Profil> findAll() {
        return repo.findAll();
    }

    @Override
    public Profil findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Profil create(Profil entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public Profil update(Profil entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //Profil banque = entity.clone(); banque.setId(null);
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
    public void delete(Profil entity) {
        endDelete(entity);
    }

    private void endDelete(Profil entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(Profil entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
