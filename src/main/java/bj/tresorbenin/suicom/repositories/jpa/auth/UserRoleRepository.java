package bj.tresorbenin.suicom.repositories.jpa.auth;

import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.entities.administration.UserRole;
import bj.tresorbenin.suicom.repositories.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserRoleRepository extends BaseRepository<UserRole> {

    @Query("FROM #{#entityName} WHERE user = ?1 AND dateCessation = 'Infinity'")
    Set<UserRole> findByUser(User user);
}
