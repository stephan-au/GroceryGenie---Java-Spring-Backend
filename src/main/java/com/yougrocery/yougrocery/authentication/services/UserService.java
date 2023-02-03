package com.yougrocery.yougrocery.authentication.services;

import com.yougrocery.yougrocery.authentication.models.User;
import com.yougrocery.yougrocery.authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepo;

    public User getUser(int id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
