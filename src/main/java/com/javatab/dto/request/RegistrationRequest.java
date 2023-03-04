package com.javatab.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.javatab.model.base.ModelBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest extends ModelBase {
	private static final long serialVersionUID = 7151443507829405471L;

	@NotNull(message = "Password must be provided")
	private String password;
	@Email(message = "Email id should be valid")
	private String email;


	@NotNull()
	private String firstName;

	@NotNull()
	private String lastName;

	@NotNull
	private String age;
	private String address;
	@NotNull
	private String tel;
	@NotNull
	private String speciality;
	@NotNull
	private String levelStudy;
	@NotNull
	private String professionalExperience;
}
