package com.anjox.api_carteira.service;

import com.anjox.api_carteira.Exeption.ErrorExeption;
import com.anjox.api_carteira.dto.LoginResponseDTO;
import com.anjox.api_carteira.dto.ResponseUserDTO;
import com.anjox.api_carteira.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${api.security.token.secret}")
    private String secret;

    public LoginResponseDTO generateToken(UserEntity user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("nome da aplicaçao")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpiration())
                    .sign(algorithm);

            ResponseUserDTO userdto = new ResponseUserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getWallet()
            );
            return new LoginResponseDTO(
                    token,
                    userdto
            );
        }catch (JWTCreationException exception){
            throw new ErrorExeption("erro ao gerar token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("nome da aplicaçao")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpiration(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
