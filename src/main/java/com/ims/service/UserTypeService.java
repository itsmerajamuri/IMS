package com.ims.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.dto.UserTypeDto;
import com.ims.entity.UserDetails;
import com.ims.entity.UserType;
import com.ims.helperclass.AddRoleHelperClass;
import com.ims.logging.ImsLogger;
import com.ims.repository.UserDetailsRepo;
import com.ims.repository.UserTypeRepo;

@Service
public class UserTypeService {

	@Autowired
	private UserTypeRepo repo;

	private ImsLogger imsLogger = null;
	@Autowired
	private UserDetailsRepo detailsRepo;

	@Autowired
	private ModelMapper mapper;

	public List<UserTypeDto> getAllUserTypes() {
		imsLogger.log("fetching usertypes from service");
		try {
			List<UserType> types = repo.findAll();

			List<UserTypeDto> typeDtos = types.stream().map((x) -> mapper.map(x, UserTypeDto.class))
					.collect(Collectors.toList());
			imsLogger
					.log("Fetching UserTypes From Service Completed and Fetched and havingf Size : " + typeDtos.size());

			return typeDtos;
		} catch (Exception e) {
			// TODO: handle exception
			imsLogger.log("Fetching UserTypes From Service Failed With Exception " + e);

			return null;
		}
	}

	public boolean addRole(AddRoleHelperClass d) {
		imsLogger.log("addrole service initiated");
		Optional<UserType> optional = repo.findByUserType("ADMIN");
		
		    
			Set<UserType> types=new HashSet<>();
			types.add(optional.get());			
			Optional<UserDetails>  optional1= detailsRepo.findByUserNameAndRolesIn(d.getUserName(), types);
             if(optional1.isEmpty()) {
     			imsLogger.log("user doesnot have right to add role");

            	 return false;
             }else {
            	 UserType type=new UserType();
            	 type.setUserType(d.getRole());
            	 repo.save(type);
      			imsLogger.log("role added successfully");
                imsLogger.log("end of addrole service");
            	 return true;
             }
		}
	

	public void setAll(ImsLogger logger) {
		this.imsLogger = logger;
	}

}
