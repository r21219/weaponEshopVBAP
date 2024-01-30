package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.config.JwtService;
import cz.osu.weaponeshop.exception.ForbiddenException;
import cz.osu.weaponeshop.exception.UserAlreadyExistsException;
import cz.osu.weaponeshop.exception.NotFoundException;
import cz.osu.weaponeshop.model.Role;
import cz.osu.weaponeshop.model.User;
import cz.osu.weaponeshop.model.dto.LoginRequest;
import cz.osu.weaponeshop.model.dto.RegisterRequest;
import cz.osu.weaponeshop.model.dto.UpdateRequest;
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

    public void updateUser(UpdateRequest userDTO) {
        if (userRepo.existsByUserName(userDTO.getNewUserName())) {
          throw new UserAlreadyExistsException("User by that name already exists");
        }
        User user = userRepo.findByUserName(userDTO.getCurrentUserName()).orElseThrow(
                () -> new NotFoundException("User was not found"));
        user.setUserName(userDTO.getNewUserName());
        userRepo.save(user);
    }

    public void deleteUser(RegisterRequest userDTO) {
        User user = userRepo.findByUserName(userDTO.getUserName()).orElseThrow(
                () -> new NotFoundException("User was not found"));
        userRepo.delete(user);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepo.findByUserName(request.getUserName()).isPresent()){
            throw new UserAlreadyExistsException("User by that name already exists");
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
        var user = userRepo.findByUserName(request.getUserName())
                .orElseThrow();
        if (!argon2.verify(user.getPassword(), request.getPassword().toCharArray())) {
            throw new ForbiddenException("Invalid password");
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
