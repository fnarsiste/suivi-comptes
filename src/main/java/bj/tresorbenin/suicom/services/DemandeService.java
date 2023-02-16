package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Demande;
import bj.tresorbenin.suicom.repositories.jpa.DemandeRepository;
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
    public Demande create(Demande entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public Demande update(Demande entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //Demande demande = entity.clone(); demande.setId(null);
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
    public void delete(Demande entity) {
        endDelete(entity);
    }

    private void endDelete(Demande entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(Demande entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
