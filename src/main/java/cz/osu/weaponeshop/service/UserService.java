package cz.osu.weaponeshop.service;

import cz.osu.weaponeshop.model.dto.RegisterRequest;

public interface UserService {
    boolean updateUser(RegisterRequest userDTO);
    boolean deleteUser(RegisterRequest userDTO);
}
