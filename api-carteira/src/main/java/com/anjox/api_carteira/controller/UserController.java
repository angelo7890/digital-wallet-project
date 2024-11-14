package com.anjox.api_carteira.controller;

import com.anjox.api_carteira.Exeption.ErrorExeption;
import com.anjox.api_carteira.dto.*;
import com.anjox.api_carteira.entity.UserEntity;
import com.anjox.api_carteira.service.JWTService;
import com.anjox.api_carteira.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid RequestAuthenticationDTO dto){
        var usernamepassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamepassword);
        LoginResponseDTO login = jwtService.generateToken((UserEntity) auth.getPrincipal());
        return ResponseEntity.ok().body(login);

    }

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDTO> CreateUser(@RequestBody @Valid RequestCreateUserDTO dto){
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
         UserEntity user = userService.create(dto, usernameFromToken);
         ResponseUserDTO response = new ResponseUserDTO(
                 user.getId(),
                 user.getUsername(),
                 user.getEmail(),
                 user.getWallet());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers(){
        List<ResponseUserDTO> response = userService.findAll();
        if (response.isEmpty()) {
            throw new ErrorExeption("nenhum usuario encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable("id") Long id){
        UserEntity user = userService.findById(id);
        if(user == null) {
            throw  new ErrorExeption("usuario nao encontrado", HttpStatus.NOT_FOUND);
        }
        ResponseUserDTO response = new ResponseUserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getWallet()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody @Valid RequestDeleteUserDTO dto){
        String userIdFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUser(dto, userIdFromToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
