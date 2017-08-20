package com.pintabar.usermanagement.service;

import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.usermanagement.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created by lucasgodoy on 12/03/17.
 */
public interface UserManagementService {

	Optional<UserDTO> getUser(String uuid);

	Optional<UserDTO> getUserByUserName(String username);

	Optional<UserDTO> getUserByEmail(String email);

	List<UserDTO> getUsers(Boolean isDeleted);

	Optional<UserDTO> createUser(UserDTO userDTO);

	Optional<UserDTO> deleteUser(String uuid);

	Boolean isValidUser(String uuid) throws DataNotFoundException;
}
