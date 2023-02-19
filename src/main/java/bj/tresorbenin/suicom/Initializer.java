package bj.tresorbenin.suicom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class Initializer {
    @PersistenceContext
    EntityManager em;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
    }
}
