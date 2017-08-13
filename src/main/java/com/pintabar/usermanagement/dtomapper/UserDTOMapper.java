package com.pintabar.usermanagement.dtomapper;

import com.pintabar.dtomappers.GenericDTOMapper;
import com.pintabar.usermanagement.dto.UserDTO;
import com.pintabar.usermanagement.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Created by lucasgodoy on 21/03/17.
 */
@Component
public class UserDTOMapper implements GenericDTOMapper<User, UserDTO> {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Optional<UserDTO> mapToDTO(@Nullable User entity) {
		UserDTO dto = null;
		if (entity != null) {
			dto = UserDTO.builder()
					.username(entity.getUsername())
					.email(entity.getEmail())
					.deleted(entity.isDeleted())
					.build();
			dto.setUuid(entity.getUuid());
			dto.setCreatedOn(entity.getCreatedOn());
			dto.setUpdatedOn(entity.getUpdatedOn());
		}
		return Optional.ofNullable(dto);
	}
}
