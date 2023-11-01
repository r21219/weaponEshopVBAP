package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.model.User;
import cz.osu.weaponeshop.model.dto.UserDTO;
import cz.osu.weaponeshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    @Override
    public boolean loginUser(UserDTO userDTO) {
        return userRepo.existsByUserNameAndPassword(userDTO.getUserName(),userDTO.getPassword());
    }

    @Override
    public boolean register(UserDTO userDTO) {
        if (!userRepo.existsByUserName(userDTO.getUserName())){
            User newUser = modelMapper.map(userDTO,User.class);
            userRepo.save(newUser);
        }else {
            throw new RuntimeException("User already exists");
        }
        return true;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        return false;
    }

    @Override
    public boolean deleteUser(UserDTO userDTO) {
        return false;
    }
}
