package com.ims.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.dto.UserDetailsDto;
import com.ims.dto.UserTypeDto;
import com.ims.entity.UserDetails;
import com.ims.entity.UserType;
import com.ims.logging.ImsLogger;
import com.ims.repository.UserDetailsRepo;
import com.ims.repository.UserTypeRepo;

@Service
public class UserDetailsService {

	private ImsLogger imsLogger = null;

	@Autowired
	private UserDetailsRepo userDetailsRepo;
	@Autowired
	private UserTypeRepo UserTypeRepo;
	@Autowired
	private ModelMapper mapper;

	public UserDetailsDto addUser(UserDetailsDto detailsDto) throws UnsupportedEncodingException {

		imsLogger.log("adduser service initated");
		Optional<UserType> type = UserTypeRepo.findByUserType("USER");

		if (type.isEmpty()) {
			imsLogger.log("no roles found");
			throw new ArithmeticException("usertype not found");
		}
		UserType type2 = type.get();
		UserTypeDto dto = mapper.map(type2, UserTypeDto.class);
		String password = detailsDto.getPassword();
		Set<UserTypeDto> dtos = new HashSet<>();
		dtos.add(dto);
		detailsDto.setRoles(dtos);
		UserDetails details = mapper.map(detailsDto, UserDetails.class);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");

			byte[] messageDigest = md.digest(password.getBytes());

			String HsPassword = new String(Base64.getEncoder().encode(messageDigest), "UTF-8");

			details.setPassword(HsPassword);
			details = userDetailsRepo.save(details);

		} catch (NoSuchAlgorithmException e) {

			imsLogger.log("no alogorithm found ");

		}
		imsLogger.log("add userService is completed");
		return mapper.map(details, UserDetailsDto.class);
	}

	public UserDetailsDto getUserDetailsByNameAndType(String username, String roles) {
		imsLogger.log("getuser by name and type service initiated");
		Optional<UserType> optional = UserTypeRepo.findByUserType(roles);
		if (optional.isEmpty()) {
			imsLogger.log("user is not found");
		}
		UserType type = optional.get();
		Set<UserType> set = new HashSet<>();
		set.add(type);
		Optional<UserDetails> optional2 = userDetailsRepo.findByUserNameAndRolesIn(username, set);

		UserDetails details = optional2.get();
		UserDetailsDto detailsDto = mapper.map(details, UserDetailsDto.class);
		imsLogger.log("getuser by name and type service is completed");
		return detailsDto;
	}

	public void setAll(ImsLogger logger) {
		this.imsLogger = logger;
	}

}
