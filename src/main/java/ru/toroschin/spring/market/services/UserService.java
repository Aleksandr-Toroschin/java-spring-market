package ru.toroschin.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.dtos.JwtResponse;
import ru.toroschin.spring.market.dtos.NewUserDto;
import ru.toroschin.spring.market.dtos.UserDto;
import ru.toroschin.spring.market.error_handling.MarketError;
import ru.toroschin.spring.market.error_handling.UserEnableException;
import ru.toroschin.spring.market.models.Authority;
import ru.toroschin.spring.market.models.Role;
import ru.toroschin.spring.market.models.User;
import ru.toroschin.spring.market.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findUserByUsername(String name) {
        return userRepository.findUsersByName(name);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUsersByName(s);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Не найден пользователь "+s));
        Collection<SimpleGrantedAuthority> rolesAndAuthorities = mappedRoles(user.getRoles());
        rolesAndAuthorities.addAll(mappedAuthorities(user.getAuthorities()));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), rolesAndAuthorities);
    }

    private Collection<SimpleGrantedAuthority> mappedRoles(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.addAll(role.getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.getName())).collect(Collectors.toList()));
        }
        return authorities;
    }

    private Collection<SimpleGrantedAuthority> mappedAuthorities(Collection<Authority> authorities) {
        return authorities.stream().map(a -> new SimpleGrantedAuthority(a.getName())).collect(Collectors.toList());
    }

    public ResponseEntity<?> registrationNewUser(NewUserDto newUserDto) {
        if (userRepository.findUsersByName(newUserDto.getUserName()).isPresent()) {
            return new ResponseEntity<>(new MarketError(HttpStatus.NOT_ACCEPTABLE.value(), "Пользователь с таким именем уже существует!"), HttpStatus.NOT_ACCEPTABLE);
        }
        newUserDto.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        User user = new User(newUserDto);
        userRepository.save(user);
        return ResponseEntity.ok(new UserDto(user));
    }
}
