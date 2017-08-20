package com.pintabar.usermanagement.model.dto;

import com.pintabar.dto.BaseDTO;
import com.pintabar.usermanagement.model.dtoentityinterface.IUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by lucasgodoy on 21/03/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
public class UserDTO extends BaseDTO implements IUser {

	@NotNull
	@Size(min = 5, max = 255)
	private String username;

	@Email
	private String email;

	private boolean deleted = false;
}
