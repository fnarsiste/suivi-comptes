package bj.tresorbenin.suicom.services.referentiels;

import bj.tresorbenin.suicom.entities.referentiels.Agent;
import bj.tresorbenin.suicom.repositories.jpa.referentiels.AgentRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService extends AbstractBaseService<Agent> {

    @Autowired
    @Getter
    private AgentRepository repository;

    public AgentService() {
        super(Agent.class);
    }
}
