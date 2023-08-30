package com.club.services;

import com.club.entities.Role;
import com.club.entities.User;
import com.club.repositories.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getById(final String guid) {
        return userRepository.findById(guid);
    }

    public void saveUser(final User user) {
        final User userByEmail = userRepository.findByEmail(user.getEmail());

        if (Objects.nonNull(userByEmail)) {
            throw new ValidationException();
        }

        user.setRoles(Set.of(Role.USER));
        final String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setNonLocked(true);
        userRepository.save(user);
    }

    public void editUser(final User user) {
        userRepository.save(user);
    }

    public void saveUserByAdmin(final User user) {
        final User userByEmail = userRepository.findByEmail(user.getEmail());

        if (Objects.nonNull(userByEmail)) {
            throw new ValidationException();
        }

        user.setNonLocked(true);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
