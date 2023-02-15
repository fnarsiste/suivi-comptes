package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Demande;
import bj.tresorbenin.suicom.repositories.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

@Service
public class DemandeService implements CrudService<Demande, Long>{


    @Autowired
    private DemandeRepository repo;

    @Override
    public List<Demande> findAll() {
        return repo.findAll();
    }

    @Override
    public Demande findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Demande save(Demande entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Demande entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(Demande entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
