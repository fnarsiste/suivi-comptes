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

        /**
        Set<Agent> agentSet=new HashSet<>();
        repo.findAll().forEach(agentSet::add);
        return agentSet;
        */
    }

    @Override
    public Agent findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Agent save(Agent entity) {
        return repo.save(entity);
    }

    @Override
    public void delete(Agent entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void beforeCreate(Agent entity) {
        entity.setDateCreation(entity.getDateCreation() == null ? new Date() : entity.getDateCreation());
        entity.setDateCessation(stringIntoDateWithFormat("31/12/9999", "dd/MM/yyyy"));
        entity.setModifierPar("N/A");
    }
}
