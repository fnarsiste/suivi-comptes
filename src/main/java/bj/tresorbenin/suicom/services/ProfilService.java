package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Banque;
import bj.tresorbenin.suicom.entities.Profil;
import bj.tresorbenin.suicom.repositories.BanqueRepository;
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
    public Profil save(Profil entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Profil entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(Profil entity) {
            entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
            entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
            entity.setModifierPar("N/A");
    }
}
