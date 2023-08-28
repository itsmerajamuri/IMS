package com.ims.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.entity.Location;
import com.ims.entity.UserDetails;
import com.ims.entity.UserType;
import com.ims.helperclass.LocationRequest;
import com.ims.logging.ImsLogger;
import com.ims.repository.LocationRepo;
import com.ims.repository.UserDetailsRepo;
import com.ims.repository.UserTypeRepo;

@Service
public class LocationService {

	@Autowired
	private LocationRepo locationRepo;
	private ImsLogger imsLogger = null;
	@Autowired
	private UserDetailsRepo detailsRepo;
	@Autowired
	private UserTypeRepo repo;
	
	public int addLocation(LocationRequest locationRequest) {
		imsLogger.log("addrole service initiated");
		Optional<UserType> optional = repo.findByUserType("ADMIN");
		
		    
			Set<UserType> types=new HashSet<>();
			types.add(optional.get());			
			Optional<UserDetails>  optional1= detailsRepo.findByUserNameAndRolesIn(locationRequest.getUserName(), types);
			if(optional.get()==null) {
				imsLogger.log("admin is not available");
				return 0;
			}
			else if(optional1.get()==null) {
     			imsLogger.log("user doesnot have right to add location");

            	 return 1;
             }else {
            	Location d=new Location();
            	d.setLocation(locationRequest.getLocation());
            	 locationRepo.save(d);
      			imsLogger.log("location added successfully");
                imsLogger.log("end of location service");
            	 return 2;
             }
	}
	public void setAll(ImsLogger logger) {
		this.imsLogger = logger;
	}
}
