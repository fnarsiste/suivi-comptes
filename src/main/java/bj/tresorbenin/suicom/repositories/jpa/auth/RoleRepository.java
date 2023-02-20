package bj.tresorbenin.suicom.repositories.jpa.auth;

import bj.tresorbenin.suicom.entities.administration.Role;
import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.repositories.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends BaseRepository<Role> {

    @Query("FROM #{#entityName} p where p.dateCessation = 'infinity' " +
            "and p.id not in (" +
            "   select pu.role from UserRole pu " +
            "   where pu.role = p and pu.user = ?1 " +
            "   and pu.dateCessation = 'infinity'" +
            ")")
    public Set<Role> getNotActiveRolesByUser(User user);
}
