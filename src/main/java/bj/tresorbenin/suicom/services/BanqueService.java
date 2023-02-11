package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Banque;
import bj.tresorbenin.suicom.repositories.BanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Banque save(Banque entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Banque entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
