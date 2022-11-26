package lt.vu.jwtauth.service;

import lt.vu.jwtauth.domain.Role;
import lt.vu.jwtauth.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);

    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    List<User> getUsers();
}
