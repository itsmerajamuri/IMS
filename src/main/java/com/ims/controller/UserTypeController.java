package com.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.config.ErrorConfig;
import com.ims.dto.UserTypeDto;
import com.ims.helperclass.AddRoleHelperClass;
import com.ims.logging.ImsLogger;
import com.ims.response.ApiResponse;
import com.ims.response.UserTypeApiResponse;
import com.ims.service.UserTypeService;

@RestController
@RequestMapping("/ims")
public class UserTypeController {
	
	@Autowired
	private ErrorConfig config;
	
	@Autowired
	private UserTypeService service;
	@GetMapping("/userTypes")
	public ResponseEntity<UserTypeApiResponse> getAllUserTypes(){
		ImsLogger logger=new ImsLogger(UserTypeController.class);
		logger.log("userType Api is Initiated");
		service.setAll(logger);
		List<UserTypeDto> list= service.getAllUserTypes();
		UserTypeApiResponse apiResponse=new UserTypeApiResponse();
		if(list.size()>0) {
			apiResponse.setResponseCode(Integer.parseInt(config.SUCCESS_CODE));
			apiResponse.setGetUserTypes(list);
		}else {
			apiResponse.setResponseCode(Integer.parseInt(config.SUCCESS_CODE));
		}
		logger.log("usertype api is completed");
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
		
	}
	@PostMapping("/addRole")
	public ResponseEntity<ApiResponse<String>> addRole(@RequestBody AddRoleHelperClass helperClass){
		ImsLogger logger=new ImsLogger(UserTypeController.class);
		logger.log("addrole Api is Initiated");
		service.setAll(logger);
		boolean b= service.addRole(helperClass);
		ApiResponse<String> apiResponse=new ApiResponse<>();
		if(b) {
			apiResponse.setStatusCode(Integer.parseInt(config.SUCCESS_CODE));
			apiResponse.setMessage("role added successfully");
			return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.ACCEPTED);
		}else {
			apiResponse.setStatusCode(Integer.parseInt(config.INVALID_USER_TYPES));
			apiResponse.setMessage("role not added");
			return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.NOT_FOUND);
		}
		
	}
	

}
