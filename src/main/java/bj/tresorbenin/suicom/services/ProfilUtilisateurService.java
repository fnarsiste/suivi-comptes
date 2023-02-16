package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.ProfilUtilisateur;
import bj.tresorbenin.suicom.repositories.jpa.ProfilUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

public class ProfilUtilisateurService implements CrudService<ProfilUtilisateur,Long>{
    @Autowired
   ProfilUtilisateurRepository repo;

    @Override
    public List<ProfilUtilisateur> findAll() {
        return repo.findAll();
    }

    @Override
    public ProfilUtilisateur findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public ProfilUtilisateur create(ProfilUtilisateur entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public ProfilUtilisateur update(ProfilUtilisateur entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //ProfilUtilisateur banque = entity.clone(); banque.setId(null);
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
    public void delete(ProfilUtilisateur entity) {
        endDelete(entity);
    }

    private void endDelete(ProfilUtilisateur entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(ProfilUtilisateur entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
