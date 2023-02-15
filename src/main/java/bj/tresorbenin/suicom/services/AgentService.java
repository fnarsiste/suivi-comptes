package bj.tresorbenin.suicom.services;

import bj.tresorbenin.suicom.entities.Agent;
import bj.tresorbenin.suicom.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static bj.tresorbenin.suicom.utils.JavaUtils.stringIntoDateWithFormat;

@Service
public class AgentService implements CrudService<Agent, Long>{


    @Autowired
    private AgentRepository repo;

    @Override
    public List<Agent> findAll() {
        return repo.findAll();
    }

    @Override
    public Agent findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Agent create(Agent entity) {
        beforeCreate(entity);
        return repo.save(entity);
    }

    @Override
    public Agent update(Agent entity) {
        // Conserver les modifications de l'utilisateur en clonant
        Agent agent = entity.clone();
        agent.setId(null);
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
    public void delete(Agent entity) {
        endDelete(entity);
    }

    private void endDelete(Agent entity) {
        if(entity == null) return;
        entity.setDateCessation(new Date());
        entity.setModifierPar("N/A");
        repo.save(entity);
    }

    @Override
    public void beforeCreate(Agent entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
