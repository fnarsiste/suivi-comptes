package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Demande;
import bj.tresorbenin.suicom.repositories.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
