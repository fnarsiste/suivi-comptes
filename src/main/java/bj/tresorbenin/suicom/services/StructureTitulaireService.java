package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.StructureTitulaire;
import bj.tresorbenin.suicom.repositories.StructureTitulaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StructureTitulaireService implements CrudService<StructureTitulaire, Long>{


    @Autowired
    private StructureTitulaireRepository repo;

    @Override
    public List<StructureTitulaire> findAll() {
        return repo.findAll();
    }

    @Override
    public StructureTitulaire findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public StructureTitulaire save(StructureTitulaire entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(StructureTitulaire entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
