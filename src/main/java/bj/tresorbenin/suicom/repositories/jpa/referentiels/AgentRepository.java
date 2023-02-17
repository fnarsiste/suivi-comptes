package bj.tresorbenin.suicom.repositories.jpa.referentiels;


import bj.tresorbenin.suicom.entities.referentiels.Agent;
import bj.tresorbenin.suicom.repositories.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AgentRepository extends BaseRepository<Agent> {

    //@Query("FROM #{#entityName} WHERE dateCessation = 'Infinity' ORDER BY id DESC")
    @Query("SELECT a FROM Agent a WHERE a.dateCessation = 'Infinity' " +
            "and a.id NOT IN (SELECT u.agent.id FROM Utilisateur u WHERE u.dateCessation = 'Infinity') " +
            "ORDER BY lastName, firstName")
    Set<Agent> getAccountableAgent();

}
