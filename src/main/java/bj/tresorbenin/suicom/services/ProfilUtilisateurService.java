package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.ProfilUtilisateur;
import bj.tresorbenin.suicom.repositories.ProfilUtilisateurRepository;
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
    public ProfilUtilisateur save(ProfilUtilisateur entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(ProfilUtilisateur entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(ProfilUtilisateur entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
