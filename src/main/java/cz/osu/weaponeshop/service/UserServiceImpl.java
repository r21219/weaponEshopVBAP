package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.config.JwtService;
import cz.osu.weaponeshop.model.Role;
import cz.osu.weaponeshop.model.User;
import cz.osu.weaponeshop.model.dto.LoginRequest;
import cz.osu.weaponeshop.model.dto.RegisterRequest;
import cz.osu.weaponeshop.model.response.AuthenticationResponse;
import cz.osu.weaponeshop.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Argon2 argon2 = Argon2Factory.create();
    //TODO might be a good idea to delete modelMapper if I won't be using it


    @Override
    public boolean updateUser(RegisterRequest userDTO) {
        if (userRepo.existsByUserNameAndPassword(userDTO.getUserName(), userDTO.getPassword())) {

        }
        return false;
    }

    @Override
    public boolean deleteUser(RegisterRequest userDTO) {
        return false;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepo.findByUserName(request.getUserName()).isPresent()){
            throw new BadCredentialsException("Username is already in use");
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
            throw new BadCredentialsException("Invalid password");
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
