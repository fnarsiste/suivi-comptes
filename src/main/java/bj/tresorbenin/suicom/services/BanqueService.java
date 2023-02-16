package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Banque;
import bj.tresorbenin.suicom.repositories.BanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BanqueService implements CrudService<Banque, Long>{

    @Autowired
    private BanqueRepository repo;

    @Override
    public List<Banque> findAll() {
        return repo.findAll();
    }

    @Override
    public Banque findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Banque create(Banque entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public Banque update(Banque entity) {
        // Conserver les modifications de l'utilisateur en clonant
        Banque banque = entity.clone();
        banque.setId(null);
        // Ici, supprimons l'ancien de la base
        deleteById(entity.getId());
        // Créer un nouvel enregistrement a parttir du clone
        return create(banque);
    }

    @Override
    public void deleteById(Long id) {
        endDelete(findById(id));
    }

    @Override
    public void delete(Banque entity) {
        endDelete(entity);
    }

    private void endDelete(Banque entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(Banque entity) {
        entity.setCode(entity.getCode().toUpperCase());
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        //entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
