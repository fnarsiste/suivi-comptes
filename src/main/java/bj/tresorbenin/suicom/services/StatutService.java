package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Statut;
import bj.tresorbenin.suicom.repositories.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
