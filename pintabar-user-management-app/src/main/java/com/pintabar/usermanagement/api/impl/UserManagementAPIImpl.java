package com.pintabar.usermanagement.api.impl;

import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.usermanagement.api.UserManagementAPI;
import com.pintabar.usermanagement.dto.UserDTO;
import org.springframework.stereotype.Component;
import com.pintabar.usermanagement.service.UserManagementService;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

import static com.pintabar.commons.exceptions.ErrorCode.USER_ALREADY_EXISTS;
import static com.pintabar.commons.exceptions.ErrorCode.USER_NOT_FOUND;

/**
 * @author Lucas.Godoy on 12/08/17.
 */
@Component
public class UserManagementAPIImpl implements UserManagementAPI {

	public final UserManagementService userManagementService;

	public UserManagementAPIImpl(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	public Response getUser(String uuid) throws DataNotFoundException {
		Optional<UserDTO> user = userManagementService.getUser(uuid);
		return Response.ok(user.orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND)))
				.build();
	}

	public Response getUsers(boolean isDeleted) {
		return Response.ok(userManagementService.getUsers(isDeleted)).build();
	}

	public Response createUser(UserDTO userDTO, UriInfo uriInfo) throws DataNotFoundException {
		UserDTO user = userManagementService.createUser(userDTO).orElseThrow(() -> new DataNotFoundException(USER_ALREADY_EXISTS));
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		uriBuilder.path(user.getUuid());
		return Response.created(uriBuilder.build()).build();
	}

	public Response deleteUser(String uuid) throws DataNotFoundException {
		userManagementService.deleteUser(uuid).orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND));
		return Response.accepted().build();
	}

	@Override
	public Response validateUser(String uuid) throws DataNotFoundException {
		return Response.ok(userManagementService.isValidUser(uuid)).build();
	}
}
