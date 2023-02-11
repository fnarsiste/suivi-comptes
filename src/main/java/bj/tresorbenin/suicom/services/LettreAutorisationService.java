package bj.tresorbenin.suicom.services;


import bj.tresorbenin.suicom.entities.LettreAutorisation;

import bj.tresorbenin.suicom.repositories.LettreAutorisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LettreAutorisationService implements CrudService<LettreAutorisation, Long>{


    @Autowired
    private LettreAutorisationRepository repo;

    @Override
    public List<LettreAutorisation> findAll() {
        return repo.findAll();
    }

    @Override
    public LettreAutorisation findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public LettreAutorisation save(LettreAutorisation entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(LettreAutorisation entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
