package com.ims.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailsDto {

	
	@NotBlank(message = "username cannot be blank")
	private String userName;
	@NotBlank(message = "password cannot be blank")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
    @NotBlank(message = "enter valid email")
    @Email(message = "enter valid email")
	private String email;
	//@NotBlank(message = "invalid mobile number")
	private long phoneNo;
	@NotBlank(message = "enter valid address")
	private String address;
	private String idProof;
    private Set<UserTypeDto> roles;
}
