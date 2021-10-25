package ru.mikejohn.roomBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mikejohn.roomBook.model.User;
import ru.mikejohn.roomBook.model.Role;
import ru.mikejohn.roomBook.repository.RoleRepository;
import ru.mikejohn.roomBook.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AvatarService avatarService;

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User findUserByUsername(String name) {
        avatarService.makeAvatar(name);
        return userRepository.findUserByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        System.out.println(user.toString());
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return user;
    }
}