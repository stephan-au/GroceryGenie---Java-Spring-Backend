package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.models.Role;
import com.yougrocery.yougrocery.authentication.models.User;
import com.yougrocery.yougrocery.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }

        return user;
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public User getUser(Long id) {
        return userRepo.findById(id).get();
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public Boolean delete(Long id) {
        userRepo.deleteById(id);
        return TRUE;
    }
}
