package com.bshop.auth;

import com.bshop.user.model.User;
import com.bshop.user.model.UserRepository;
import com.google.common.base.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class HibernateUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    public HibernateUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!Strings.isNullOrEmpty(username)) {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            optionalUser.orElseThrow(() -> getUsernameNotFoundException(username));
            User user = optionalUser.get();

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPwd(),
                    user.getAuthorities()
            );
        } else {
            throw getUsernameNotFoundException(username);
        }
    }

    private UsernameNotFoundException getUsernameNotFoundException(String username) {
        return new UsernameNotFoundException("The user wasn't found username:" + username);
    }
}
