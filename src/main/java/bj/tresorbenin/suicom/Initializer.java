package bj.tresorbenin.suicom;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Transactional
public class Initializer {
    //@PersistenceContext EntityManager em;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        /*
        try{
             User user = em.find(User.class, 5L);
             Role role = em.find(Role.class, 2L);
             UserRole userRole = new UserRole(false, user, role);
             em.persist(userRole);
             log.info(userRole.toString());
         } catch (Exception e) {
             log.info(e.getMessage());
         }
        */
    }
}
