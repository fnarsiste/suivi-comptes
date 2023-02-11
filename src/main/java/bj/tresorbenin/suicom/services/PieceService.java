package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Piece;
import bj.tresorbenin.suicom.repositories.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Piece save(Piece entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Piece entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
