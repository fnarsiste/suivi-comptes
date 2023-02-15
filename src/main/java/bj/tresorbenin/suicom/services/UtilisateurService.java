package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Utilisateur;
import bj.tresorbenin.suicom.repositories.ProfilRepository;
import bj.tresorbenin.suicom.repositories.UtilisateurRepository;
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
    public Utilisateur save(Utilisateur entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Utilisateur entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(Utilisateur entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
