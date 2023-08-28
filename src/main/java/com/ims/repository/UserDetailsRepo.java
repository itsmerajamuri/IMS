package com.ims.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.entity.UserDetails;
import com.ims.entity.UserType;
@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer>{

	public abstract Optional<UserDetails> findByUserNameAndRolesIn(String username, Set<UserType> roles);
	
	public UserDetails findByUserNameAndPassword(String username,String password);
}
