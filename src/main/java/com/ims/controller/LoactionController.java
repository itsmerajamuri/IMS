package com.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.config.ErrorConfig;
import com.ims.helperclass.LocationRequest;
import com.ims.logging.ImsLogger;
import com.ims.response.ApiResponse;
import com.ims.service.LocationService;

@RestController
@RequestMapping("/ims")
public class LoactionController {
	
	@Autowired
	private ErrorConfig config;
	
	@Autowired
	private LocationService locationService;
	
	@PostMapping("/addLocation")
	public ResponseEntity<ApiResponse<String>> addLocation(@RequestBody LocationRequest locationRequest) {
		ImsLogger logger=new ImsLogger(UserTypeController.class);
		logger.log("addrole Api is Initiated");
		locationService.setAll(logger);
		int result= locationService.addLocation(locationRequest);
		ApiResponse<String> apiResponse=new ApiResponse<>();
		if(result==0) {
			apiResponse.setStatusCode(Integer.parseInt(config.INVALID_USER_TYPES));
			apiResponse.setMessage("Admin is not availble");
			return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.ACCEPTED);
		}else if(result==1) {
			apiResponse.setStatusCode(Integer.parseInt(config.INVALID_USER_TYPES));
			apiResponse.setMessage("doesn't have right to add location");
			return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.ACCEPTED);
		}else {
			apiResponse.setStatusCode(Integer.parseInt(config.INVALID_USER_TYPES));
			apiResponse.setMessage("location added successfully");
			return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.ACCEPTED);
		}
	}

	
}
