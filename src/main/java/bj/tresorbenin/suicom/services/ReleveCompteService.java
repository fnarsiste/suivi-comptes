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
    public ReleveCompte create(ReleveCompte entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public ReleveCompte update(ReleveCompte entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //ReleveCompte banque = entity.clone(); banque.setId(null);
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
    public void delete(ReleveCompte entity) {
        endDelete(entity);
    }

    private void endDelete(ReleveCompte entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(ReleveCompte entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
