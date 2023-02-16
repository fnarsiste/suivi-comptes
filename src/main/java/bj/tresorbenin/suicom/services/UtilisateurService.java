package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Utilisateur;
import bj.tresorbenin.suicom.repositories.jpa.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

public class UtilisateurService implements CrudService<Utilisateur,Long>{
    @Autowired
    private UtilisateurRepository repo;

    @Override
    public List<Utilisateur> findAll() {
        return repo.findAll();
    }

    @Override
    public Utilisateur findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Utilisateur create(Utilisateur entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public Utilisateur update(Utilisateur entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //Utilisateur banque = entity.clone(); banque.setId(null);
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
    public void delete(Utilisateur entity) {
        endDelete(entity);
    }

    private void endDelete(Utilisateur entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(Utilisateur entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
