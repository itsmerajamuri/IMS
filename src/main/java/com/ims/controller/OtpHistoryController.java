package com.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.config.ErrorConfig;
import com.ims.dto.OtpHistoryDto;
import com.ims.helperclass.LoginObj;
import com.ims.logging.ImsLogger;
import com.ims.response.ApiResponse;
import com.ims.service.OtpHistoryService;

@RestController
@RequestMapping("/ims")
public class OtpHistoryController {

	@Autowired
	private ErrorConfig config;
	@Autowired
	private OtpHistoryService historyService;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login( @RequestBody LoginObj loginObj){
		ImsLogger imsLogger=new ImsLogger(OtpHistoryController.class);
		historyService.setAll(imsLogger);
		imsLogger.log("login api initiated");
		boolean b= historyService.login(loginObj);
		ApiResponse<String> apiResponse=new ApiResponse<>();
		if(b) {
			apiResponse.setStatusCode(Integer.parseInt(config.SUCCESS_CODE));
			apiResponse.setMessage("otp send successfully");
			imsLogger.log("end of login api and otp send successfuly");
			return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.ACCEPTED);
		}else {
			apiResponse.setStatusCode(Integer.parseInt(config.INVALID_USER_ID));
			apiResponse.setMessage("otp failed to send");
			imsLogger.log("end of login api and otp failed to send");
			return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/validateOtp")
	public ResponseEntity<ApiResponse<String>> valiadteOtp(@RequestBody OtpHistoryDto dto){
		ImsLogger imsLogger=new ImsLogger(OtpHistoryController.class);
		historyService.setAll(imsLogger);
		imsLogger.log("validate Otp api initiated");
	    String history=historyService.validateOtpAndRefId(dto);
	    ApiResponse<String> apiResponse=new ApiResponse<>();
	    if(history.equals(null)) {
	    	
	    	apiResponse.setStatusCode(Integer.parseInt(config.INVALID_USER_ID));
			apiResponse.setMessage(history);
			imsLogger.log("end of validate otp api and valiadtion failed");
			return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.NOT_FOUND);
		}else if(history.equals("otp session expired")){
			apiResponse.setStatusCode(Integer.parseInt(config.INVALID_USER_ID));
			apiResponse.setMessage(history);
			imsLogger.log("end of validation api and otp ");
			return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.ACCEPTED);
		}else {
			apiResponse.setStatusCode(Integer.parseInt(config.SUCCESS_CODE));
			apiResponse.setMessage(history);
			imsLogger.log("end of validation api and otp ");
			return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.ACCEPTED);
 
			
			
		}
	    
	}
}
