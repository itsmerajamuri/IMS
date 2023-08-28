package com.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

	private int id;
    private String name;
    private String type;
    private String password;
    private String confirmPassword;
    private String email;
    private String address;
    private String idProof;
    private long phone;
    private String usernameauthority;
}
