package com.pintabar.usermanagement.api;

import com.pintabar.commons.exceptions.general.DataNotFoundException;
import com.pintabar.usermanagement.dto.UserDTO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Lucas.Godoy on 12/08/17.
 */
@Path("/user")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface UserManagementAPI {

	@GET
	@Path("/{uuid}")
	Response getUser(@NotBlank @PathParam("uuid") String uuid) throws DataNotFoundException;

	@GET
	Response getUsers(@DefaultValue("false") @QueryParam("deleted") boolean isDeleted);

	@POST
	Response createUser(@Valid UserDTO userDTO, @Context UriInfo uriInfo) throws DataNotFoundException;

	@DELETE
	@Path("/{uuid}")
	Response deleteUser(@NotBlank @PathParam("uuid") String uuid) throws DataNotFoundException;

	@GET
	@Path("/{uuid}/validation")
	Response validateUser(@NotBlank @PathParam("uuid") String uuid) throws DataNotFoundException;

}
