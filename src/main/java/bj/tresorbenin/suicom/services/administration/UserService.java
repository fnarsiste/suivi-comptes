package bj.tresorbenin.suicom.services.administration;

import bj.tresorbenin.suicom.entities.administration.Role;
import bj.tresorbenin.suicom.entities.administration.User;
import bj.tresorbenin.suicom.entities.administration.UserRole;
import bj.tresorbenin.suicom.repositories.jpa.auth.UtilisateurRepository;
import bj.tresorbenin.suicom.services.AbstractBaseService;
import bj.tresorbenin.suicom.services.referentiels.AgentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService extends AbstractBaseService<User> {
    @Autowired
    @Getter
    private UtilisateurRepository repository;

    @Autowired
    @Getter
    private RoleService roleService;

    @Autowired
    @Getter
    private UserRoleService userRoleService;

    @Autowired
    @Getter
    private AgentService agentService;

    public UserService() {
        super(User.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Long> saveUserRoles(String userId, JSONObject jsonParam) throws Exception {
        JSONArray jsonArray = (JSONArray) jsonParam.get("inactif");
        User user = this.get(userId);
        long added = 0;
        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            long userVal = Long.parseLong(json.get("userval").toString());
            if (userVal == 0) continue;
            Role role = roleService.getByCode(json.get("role").toString());
            UserRole userRole = new UserRole(true, user, role);
            userRoleService.create(userRole);
            added++;
        }
        long removed = 0;
        jsonArray = (JSONArray) jsonParam.get("actif");
        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            boolean checked = json.get("userval").toString().equals("1");
            if(!checked) {
                userRoleService.delete(Long.parseLong(json.get("id").toString()));
                removed++;
            }
        }
        return Map.of("added", added, "removed", removed);
        //throw new Exception("ROLL BACK ALL...");
    }

    public List<UserRole> getRoleByUser(User user) {
        return userRoleService.findByUser(user);
    }

    public List<Role> getNotActiveRoleByUser(User user) {
        return roleService.getNotActiveRoleByUser(user);
    }
}
