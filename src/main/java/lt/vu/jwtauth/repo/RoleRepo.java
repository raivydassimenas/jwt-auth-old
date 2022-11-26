package lt.vu.jwtauth.repo;

import lt.vu.jwtauth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRolename(String rolename);
}
