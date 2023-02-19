package bj.tresorbenin.suicom.services.administration;

import bj.tresorbenin.suicom.entities.administration.Role;
import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.repositories.jpa.auth.RoleRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends AbstractBaseService<Role> {
    @Autowired
    @Getter
    private RoleRepository repository;

    public RoleService() {
        super(Role.class);
    }

    public List<Role> getNotActiveRoleByUser(User user) {
        return toList(repository.getNotActiveRolesByUser(user));
    }
}
