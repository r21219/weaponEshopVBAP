package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.config.JwtService;
import cz.osu.weaponeshop.exception.BadRequestException;
import cz.osu.weaponeshop.exception.NotFoundException;
import cz.osu.weaponeshop.model.Role;
import cz.osu.weaponeshop.model.User;
import cz.osu.weaponeshop.model.dto.user.LoginRequest;
import cz.osu.weaponeshop.model.dto.user.RegisterRequest;
import cz.osu.weaponeshop.model.dto.user.UpdateRequest;
import cz.osu.weaponeshop.model.response.AuthenticationResponse;
import cz.osu.weaponeshop.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Argon2 argon2 = Argon2Factory.create();
    private final String USER_NOT_FOUND = "User was not found!";
    private final String USER_ALREADY_EXISTS = "User by that name already exists";
    private final String USER_OR_PASSWORD_EMPTY = "Username or password cannot be empty!";
    private final String USER_INVALID_PASSWORD = "Invalid password";

    public void updateUser(UpdateRequest userDTO) {
        if (userRepo.existsByUserName(userDTO.getNewUserName())) {
            throw new BadRequestException(USER_ALREADY_EXISTS);
        }
        User user = userRepo.findByUserName(userDTO.getCurrentUserName()).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND));
        user.setUserName(userDTO.getNewUserName());
        userRepo.save(user);
    }

    public void deleteUser(RegisterRequest userDTO) {
        if (userDTO.isEmpty()){
            throw new BadRequestException(USER_OR_PASSWORD_EMPTY);
        }
        User user = userRepo.findByUserName(userDTO.getUserName()).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND));
        if (!argon2.verify(user.getPassword(), userDTO.getPassword().toCharArray())) {
            throw new BadRequestException(USER_INVALID_PASSWORD);
        }
        userRepo.delete(user);
    }

    public String getUser(Long id) {
        if (id == null){
            throw new BadRequestException("User id cannot be empty");
        }
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return user.getUsername();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (request.isEmpty()) {
            throw new BadRequestException(USER_OR_PASSWORD_EMPTY);
        }
        if (userRepo.findByUserName(request.getUserName()).isPresent()) {
            throw new BadRequestException(USER_ALREADY_EXISTS);
        }
        var user = User.builder()
                .userName(request.getUserName())
                .password(argon2.hash(2, 65536, 1, request.getPassword().toCharArray()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        if (request.isEmpty()) {
            throw new BadRequestException(USER_OR_PASSWORD_EMPTY);
        }
        var user = userRepo.findByUserName(request.getUserName())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (!argon2.verify(user.getPassword(), request.getPassword().toCharArray())) {
            throw new BadRequestException(USER_INVALID_PASSWORD);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
