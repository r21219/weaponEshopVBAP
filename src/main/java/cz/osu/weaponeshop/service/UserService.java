package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.model.dto.UserDTO;

public interface UserService {
    boolean loginUser(UserDTO userDTO);
    boolean register(UserDTO userDTO);
    boolean updateUser(UserDTO userDTO);
    boolean deleteUser(UserDTO userDTO);
}
