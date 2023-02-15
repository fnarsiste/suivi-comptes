package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Fonction;
import bj.tresorbenin.suicom.repositories.FonctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

@Service
public class FonctionService implements CrudService<Fonction, Long>{


    @Autowired
    private FonctionRepository repo;

    @Override
    public List<Fonction> findAll() {
        return repo.findAll();
    }

    @Override
    public Fonction findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Fonction save(Fonction entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Fonction entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(Fonction entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
