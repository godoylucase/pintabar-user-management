package com.pintabar.usermanagement.service.impl;

import com.google.common.collect.Lists;
import com.pintabar.commons.exceptions.ErrorCode;
import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.usermanagement.dto.UserDTO;
import com.pintabar.usermanagement.dtomapper.UserDTOMapper;
import com.pintabar.usermanagement.entity.User;
import com.pintabar.usermanagement.repository.UserRepository;
import com.pintabar.usermanagement.repository.querydsl.UserPredicates;
import com.pintabar.usermanagement.service.UserManagementService;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Lucas.Godoy on 12/08/17.
 */
@Component
public class UserManagementServiceImpl implements UserManagementService {

	private final UserRepository userRepository;
	private final UserDTOMapper userDTOMapper;

	public UserManagementServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper) {
		this.userRepository = userRepository;
		this.userDTOMapper = userDTOMapper;
	}

	@Override
	public Optional<UserDTO> getUser(String uuid) {
		Optional<User> user = userRepository.findByUuid(uuid);
		return userDTOMapper.mapToDTO(user.orElse(null));
	}

	@Override
	public Optional<UserDTO> getUserByUserName(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return userDTOMapper.mapToDTO(user.orElse(null));
	}

	@Override
	public Optional<UserDTO> getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return userDTOMapper.mapToDTO(user.orElse(null));
	}

	@Override
	public List<UserDTO> getUsers(Boolean isDeleted) {
		Predicate searchPredicate = UserPredicates.deletedUser(isDeleted);
		List<User> users = Lists.newArrayList(userRepository.findAll(searchPredicate));
		return users.stream()
				.map(u -> userDTOMapper.mapToDTO(u).get())
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Optional<UserDTO> createUser(UserDTO userDTO) {
		User user = null;
		if (!StringUtils.isEmpty(userDTO.getEmail())
				&& !StringUtils.isEmpty(userDTO.getUsername())) {
			Optional<UserDTO> emailExist = getUserByEmail(userDTO.getEmail());
			Optional<UserDTO> usernameExist = getUserByUserName(userDTO.getUsername());

			if (!emailExist.isPresent() && !usernameExist.isPresent()) {
				user = User.builder()
						.username(userDTO.getUsername())
						.email(userDTO.getEmail())
						.build();
				user = userRepository.save(user);
			}
		}
		return userDTOMapper.mapToDTO(user);
	}

	@Override
	@Transactional
	public Optional<UserDTO> deleteUser(String uuid) {
		if (!StringUtils.isEmpty(uuid)) {
			Optional<User> user = userRepository.findByUuid(uuid);
			user.ifPresent(u -> u.setDeleted(true));
			return userDTOMapper.mapToDTO(user.orElse(null));
		}
		return Optional.empty();
	}

	@Override
	public Boolean isValidUser(String uuid) throws DataNotFoundException {
		return isValidUser(userRepository.findByUuid(uuid).orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND)));
	}

	private Boolean isValidUser(User user) {
		return user.isValid();
	}
}
