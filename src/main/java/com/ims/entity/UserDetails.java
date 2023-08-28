package com.ims.entity;


import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String userName;
	@Column
	private String password;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private long phoneNo;
	@Column(nullable = false)
	private String address;
	private String idProof;
	
	  @ManyToMany( fetch = FetchType.EAGER)
	  @JoinTable(name = "Users_Roles",joinColumns = {@JoinColumn(name=
	  "users")},inverseJoinColumns = {
	  @JoinColumn(name="roles") })
	private Set<UserType> roles;
	  
	

}
