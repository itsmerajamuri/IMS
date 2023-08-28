package com.ims.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.dto.Employee;
import com.ims.entity.UserDetails;
import com.ims.entity.UserType;
import com.ims.logging.ImsLogger;
import com.ims.repository.UserDetailsRepo;
import com.ims.repository.UserTypeRepo;

@Service
public class AddEmployeeService {

	@Autowired
	private UserDetailsRepo userDetailsRepo;

	private ImsLogger imsLogger = null;

	private static String folderPath = "e:/multipartfile/";

	@Autowired
	private UserDetailsRepo detailsRepo;
	@Autowired
	private UserTypeRepo repo;

	public int addEmployee(MultipartFile file,String employee) throws IOException {

		Optional<UserType> optional = repo.findByUserType("ADMIN");

		Set<UserType> types = new HashSet<>();
		types.add(optional.get());
		Employee emp=new Employee();
		ObjectMapper objectMapper=new ObjectMapper();
		emp=objectMapper.readValue(employee, Employee.class);
		Optional<UserDetails> optional1 = detailsRepo.findByUserNameAndRolesIn(emp.getUsernameauthority(), types);
		if (optional.get() == null) {
			imsLogger.log("admin is not available");
			return 0;
		} else if (optional1.get() == null) {
			imsLogger.log("user doesnot have right to add customer");

			return 1;
		} else {
			String filepath = folderPath + file.getOriginalFilename();
			UserDetails userDetails = new UserDetails();
			

			if (!emp.getPassword().equals(emp.getConfirmPassword())) {
				return 2;
			} else {
				File file1 = new File(filepath);
				if (!file1.exists()) {
					file1.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(file1);
				out.write(file.getBytes());
				out.close();
				userDetails.setPassword(emp.getPassword());
				userDetails.setAddress(emp.getAddress());
				userDetails.setIdProof(filepath);
				userDetails.setPhoneNo(emp.getPhone());
				userDetails.setEmail(emp.getEmail());
				Optional<UserType> details = repo.findByUserType(emp.getType());
				Set<UserType> types1 = new HashSet<>();
				types1.add(details.get());
				userDetails.setRoles(types1);
				userDetails.setUserName(emp.getName());
				try {
					MessageDigest md = MessageDigest.getInstance("SHA-512");

					byte[] messageDigest = md.digest(userDetails.getPassword().getBytes());

					String HsPassword = new String(Base64.getEncoder().encode(messageDigest), "UTF-8");

					userDetails.setPassword(HsPassword);
					imsLogger.log("employee added");
					userDetailsRepo.save(userDetails);

				} catch (NoSuchAlgorithmException e) {

					imsLogger.log("no alogorithm found ");

				}

				return 3;

			}
		}

	}

	public void setAll(ImsLogger logger) {
		this.imsLogger = logger;
	}
}
