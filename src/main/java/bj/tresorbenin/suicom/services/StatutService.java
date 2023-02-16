package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Statut;
import bj.tresorbenin.suicom.repositories.jpa.StatutRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;


@Service
@SuppressWarnings("all")
public class StatutService implements CrudService<Statut, Long>{
    private final StatutRepository repo;

    public StatutService(StatutRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Statut> findAll() {
        return repo.findAll();
    }

    @Override
    public Statut findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Statut create(Statut entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public Statut update(Statut entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //Statut banque = entity.clone(); banque.setId(null);
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
    public void delete(Statut entity) {
        endDelete(entity);
    }

    private void endDelete(Statut entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(Statut entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
