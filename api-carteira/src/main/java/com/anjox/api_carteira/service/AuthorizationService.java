package com.anjox.api_carteira.service;
import com.anjox.api_carteira.Exeption.ErrorExeption;
import com.anjox.api_carteira.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByusername(username)!=null){
            return userRepository.findByusername(username);
        }
        throw new ErrorExeption( "user name nao encontrado", HttpStatus.NOT_FOUND);
    }

}
