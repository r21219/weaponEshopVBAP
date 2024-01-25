package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.config.JwtService;
import cz.osu.weaponeshop.model.Role;
import cz.osu.weaponeshop.model.User;
import cz.osu.weaponeshop.model.dto.LoginRequest;
import cz.osu.weaponeshop.model.dto.RegisterRequest;
import cz.osu.weaponeshop.model.response.AuthenticationResponse;
import cz.osu.weaponeshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
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
        var user = User.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        //FIXME Edit the exception so it makes sense
        var user = userRepo.findByUserName(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
