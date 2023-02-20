package bj.tresorbenin.suicom.services.administration;

import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.entities.administration.UserRole;
import bj.tresorbenin.suicom.repositories.jpa.auth.UserRoleRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserRoleService extends AbstractBaseService<UserRole> {
    @Autowired
    @Getter
    UserRoleRepository repository;

    public UserRoleService() {
        super(UserRole.class);
    }

    public List<UserRole> findByUser(User user) {
        return toList(repository.findByUser(user));
    }
}
