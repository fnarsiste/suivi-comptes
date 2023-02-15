package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Statut;
import bj.tresorbenin.suicom.repositories.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

@Service
public class StatutService implements CrudService<Statut, Long>{


    @Autowired
    private StatutRepository repo;

    @Override
    public List<Statut> findAll() {
        return repo.findAll();
    }

    @Override
    public Statut findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Statut save(Statut entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Statut entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(Statut entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        //entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
