package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.ReleveCompte;
import bj.tresorbenin.suicom.repositories.ReleveCompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ReleveCompte save(ReleveCompte entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(ReleveCompte entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
