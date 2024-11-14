package com.anjox.api_carteira.service;
import com.anjox.api_carteira.Exeption.ErrorExeption;
import com.anjox.api_carteira.dto.*;
import com.anjox.api_carteira.entity.UserEntity;
import com.anjox.api_carteira.entity.WalletEntity;
import com.anjox.api_carteira.enums.UserEnum;
import com.anjox.api_carteira.repository.UserRepository;
import com.anjox.api_carteira.repository.WalletRepository;
import com.anjox.api_carteira.util.VerifyPassword;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, WalletRepository walletRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity create(RequestCreateUserDTO requestCreateUserDTO, String tokenUser){
        if(existsbyemail(requestCreateUserDTO.email()) || existsbyusername(requestCreateUserDTO.username())) {
            throw new ErrorExeption("nome ou email ja existe", HttpStatus.CONFLICT);
        }
        VerifyPassword.verifyPassword(requestCreateUserDTO.password());

        if (requestCreateUserDTO.type() == UserEnum.ADM) {
            if (tokenUser == null || tokenUser.isEmpty()) {
                throw new ErrorExeption("Token de usuário não fornecido para criação de administrador", HttpStatus.BAD_REQUEST);
            }

           UserEntity requester = userRepository.findByusername(tokenUser);
            if (requester == null || requester.getUserType() != UserEnum.ADM) {
                throw new ErrorExeption("Apenas administradores podem criar outros administradores", HttpStatus.FORBIDDEN);
            }
        }

        WalletEntity wallet = new WalletEntity( BigDecimal.ZERO );

        UserEntity user = new UserEntity(
                    requestCreateUserDTO.username(),
                    requestCreateUserDTO.email(),
                    passwordEncoder.encode(requestCreateUserDTO.password()),
                    requestCreateUserDTO.type(),
                    wallet
        );
        userRepository.save(user);
        wallet.setWalletuser(user.getId());
        walletRepository.save(wallet);



        return user;
    }

    public List<ResponseUserDTO> findAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(
                u -> new ResponseUserDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getWallet()
                )
        ).collect(Collectors.toList());
    }

    public UserEntity findById(Long id) {
        return userRepository.findByid(id);
    }

    public void deleteUser(RequestDeleteUserDTO dto , String tokenUser){
        UserEntity user = userRepository.findByid(dto.id());
        UserEntity user2 =  userRepository.findByusername(tokenUser);
        if (user == null ){
             throw new ErrorExeption("usuario nao encontrado", HttpStatus.NOT_FOUND);
        }
        if (Objects.equals(user.getUsername(), tokenUser) || user2.getUserType() == UserEnum.ADM){
            userRepository.deleteById(user.getId());
        }
        throw new ErrorExeption("voce nao pode deletar outra conta", HttpStatus.FORBIDDEN);
    }

    private boolean existsbyemail(String email){
        return userRepository.existsByemail(email);
    }
    private boolean existsbyusername(String username){
        return userRepository.existsByusername(username);
    }
}
