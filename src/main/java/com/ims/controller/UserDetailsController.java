package com.ims.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ims.config.ErrorConfig;
import com.ims.dto.Employee;
import com.ims.dto.UserDetailsDto;
import com.ims.helperclass.ValidateUser;
import com.ims.logging.ImsLogger;
import com.ims.response.ApiResponse;
import com.ims.response.UserDetailsApiResonse;
import com.ims.service.AddEmployeeService;
import com.ims.service.UserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ims")
public class UserDetailsController {
	
	@Autowired
	private ErrorConfig config;
	
	@Autowired
	private AddEmployeeService addEmployeeService;
	
	@Autowired
	private UserDetailsService detailsService;
	@PostMapping("/register")
	public ResponseEntity<UserDetailsApiResonse> adduser(@Valid @RequestBody UserDetailsDto detailsDto) throws UnsupportedEncodingException {
		ImsLogger imsLogger=new ImsLogger(UserDetailsController.class);
		imsLogger.log("addusers api initiated");
		detailsService.setAll(imsLogger);
		UserDetailsDto detailsDto2= detailsService.addUser(detailsDto);
		UserDetailsApiResonse apiResonse=new UserDetailsApiResonse();
		if(detailsDto2==null) {
			imsLogger.log("user not added please check your details");
			apiResonse.setResponseCode(Integer.parseInt(config.INVALID_USER_ID));
			apiResonse.setResponse("user not added");
		}
		else {
			imsLogger.log("user added successfully");
			apiResonse.setResponseCode(Integer.parseInt(config.SUCCESS_CODE));
			apiResonse.setResponse("user added");	
		}
		imsLogger.log("user register api completed");
		return new  ResponseEntity<>(apiResonse, HttpStatus.OK);
		
	}
	@PostMapping("/validateuser")
	public ResponseEntity<UserDetailsDto> getuserByUserNameAndType(@RequestBody ValidateUser validateUser ){
		ImsLogger imsLogger=new ImsLogger(UserDetailsController.class);
		imsLogger.log("vlidate users api initiated");
		detailsService.setAll(imsLogger);
		UserDetailsDto detailsDto= detailsService.getUserDetailsByNameAndType(validateUser.getUsername(), validateUser.getUserType());
		imsLogger.log("validate user api is completed");
		return new ResponseEntity<UserDetailsDto>(detailsDto, HttpStatus.OK);
		
	}
	@RequestMapping(value="/addEmployee", method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ApiResponse<String>> addEmployee(@RequestPart("file") MultipartFile file,@RequestPart("employee")
			String employee) throws IOException{
		ImsLogger imsLogger=new ImsLogger(UserDetailsController.class);
		addEmployeeService.setAll(imsLogger);
		int result= addEmployeeService.addEmployee(file, employee);
		
		imsLogger.log("addemployee api initiated");
		ApiResponse<String> apiResponse=new ApiResponse<>();
		if(result==0) {
			imsLogger.log("admin is not available");
			apiResponse.setStatusCode(Integer.parseInt(config.Admin_NOTAVAILABLE));
			apiResponse.setMessage("admin not available");
			
		}else if(result==1) {
			imsLogger.log("user doesnot have right to add employee");
			apiResponse.setStatusCode(Integer.parseInt(config.Admin_NOTAVAILABLE));
			apiResponse.setMessage("user doesnot have right to add employee");
			
		}
		else {
			imsLogger.log("admin available");
			apiResponse.setStatusCode(Integer.parseInt(config.SUCCESS_CODE));
			apiResponse.setMessage("user added successfully");
			

		}
		imsLogger.log("end of addemployee api");
		return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.ACCEPTED);
		
			
		
		
	}
	
	

}
