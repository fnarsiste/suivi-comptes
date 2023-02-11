package bj.tresorbenin.suicom.repositories;


import bj.tresorbenin.suicom.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
