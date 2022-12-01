package lt.vu.jwtauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.vu.jwtauth.domain.Role;
import lt.vu.jwtauth.domain.User;
import lt.vu.jwtauth.repo.RoleRepo;
import lt.vu.jwtauth.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        Optional<User> user = userRepo.findByUsername(username);
        Optional<Role> role = roleRepo.findByRolename(rolename);

        if (user.isPresent() && role.isPresent()) {
            user.get().getRoles().add(role.get());
        }
    }

    @Override
    public User getUser(String username) {
        return userRepo
                .findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepo.findByUsername(username);
//
//        if (user == null) {
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//        } else {
//            log.info("User found in the database: {}", username);
//        }
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        user.getRoles().forEach(role ->
//        {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        });
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

        return userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
