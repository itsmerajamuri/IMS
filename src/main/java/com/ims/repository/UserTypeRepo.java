package com.ims.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.entity.UserType;

public interface UserTypeRepo extends JpaRepository<UserType, Integer> {

	public Optional<UserType> findByUserType(String userType);
	
}
