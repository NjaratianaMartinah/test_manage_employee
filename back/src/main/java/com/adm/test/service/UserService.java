package com.adm.test.service;

import com.adm.test.entity.User;
import com.adm.test.dao.UserDao;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    private final static Logger log = LogManager.getLogger(UserService.class);

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> findUserByName(String username) {
        log.info("Attempting to find user with username: {}", username);
        if (StringUtils.isBlank(username)) {
            log.warn("Provided username is blank or null");
            return Optional.empty();
        }
        Optional<User> user = userDao.findUserEntityByUsername(username);
        if (user.isPresent()) {
            log.info("User found with username: {}", username);
        } else {
            log.warn("No user found with username: {}", username);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user by username: {}", username);
        return findUserByName(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UsernameNotFoundException(username);
                });
    }

    public User saveUser(User user) {
        log.info("Attempting to save user with username: {}", user.getUsername());
        User savedUser = userDao.save(user);
        log.info("User saved successfully with username: {}", user.getUsername());
        return savedUser;
    }


}
