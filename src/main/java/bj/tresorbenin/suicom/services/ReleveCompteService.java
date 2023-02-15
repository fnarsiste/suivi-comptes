package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.ReleveCompte;
import bj.tresorbenin.suicom.repositories.ReleveCompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

@Service
public class ReleveCompteService implements CrudService<ReleveCompte, Long>{


    @Autowired
    private ReleveCompteRepository repo;

    @Override
    public List<ReleveCompte> findAll() {
        return repo.findAll();
    }

    @Override
    public ReleveCompte findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public ReleveCompte save(ReleveCompte entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(ReleveCompte entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(ReleveCompte entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
