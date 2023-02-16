package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Piece;
import bj.tresorbenin.suicom.repositories.jpa.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

@Service
public class PieceService implements CrudService<Piece, Long>{


    @Autowired
    private PieceRepository repo;

    @Override
    public List<Piece> findAll() {
        return repo.findAll();
    }

    @Override
    public Piece findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Piece create(Piece entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public Piece update(Piece entity) {
        // Conserver les modifications de l'utilisateur en clonant
        //Piece banque = entity.clone(); banque.setId(null);
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
    public void delete(Piece entity) {
        endDelete(entity);
    }

    private void endDelete(Piece entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(Piece entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
