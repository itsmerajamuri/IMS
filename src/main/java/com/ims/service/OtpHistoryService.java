package com.ims.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ims.dto.OtpHistoryDto;
import com.ims.entity.OtpHistory;
import com.ims.entity.SessionHistory;
import com.ims.entity.UserDetails;
import com.ims.entity.UserType;
import com.ims.helperclass.Email;
import com.ims.helperclass.LoginObj;
import com.ims.logging.ImsLogger;
import com.ims.repository.OtpHistoryRepo;
import com.ims.repository.SessionRepo;
import com.ims.repository.UserDetailsRepo;

@Service
public class OtpHistoryService {
	
	private ImsLogger imsLogger=null;
	
	@Autowired
	private OtpHistoryRepo historyRepo;
	
	@Autowired
	private UserDetailsRepo detailsRepo;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private SessionRepo repo;
	
	
	
// login Dtails Service
	public boolean login(LoginObj loginObj) {
		imsLogger.log("login service initiated");
		
		
		
		UserDetails details= detailsRepo.findByUserNameAndPassword(loginObj.getUsername(), loginObj.getPassword());
		if(details==null) {
			imsLogger.log("no user found ");
			return false;
		}else {
			imsLogger.log("generating Otp");
			String numbers = "0123456789";
			  
	        // Using random method
	        Random rndm_method = new Random();
	  
	        char[] otp = new char[10];
	  
	        for (int i = 0; i < otp.length; i++)
	        {
	            // Use of charAt() method : to get character value
	            // Use of nextInt() as it is scanning the value as int
	            otp[i] =
	             numbers.charAt(rndm_method.nextInt(numbers.length()));
	        }
	        
	        String otp1=new String(otp);
	        
	        //alpha numeric
	        
	     // choose a Character random from this String
	        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	               + "0123456789"
	               + "abcdefghijklmnopqrstuvxyz";
	       
	        // create StringBuffer size of AlphaNumericString
	        StringBuilder sb = new StringBuilder(12);
	       
	        for (int i = 0; i < sb.capacity(); i++) {
	       
	         // generate a random number between
	         // 0 to AlphaNumericString variable length
	         int index
	          = (int)(AlphaNumericString.length()
	            * Math.random());
	       
	         // add Character one by one in end of sb
	         sb.append(AlphaNumericString
	            .charAt(index));
	        }
	       String refid1= sb.toString();
	        OtpHistory history=new OtpHistory();
	        LocalDateTime gen = LocalDateTime.now(); 
	        LocalDateTime exp = gen.plusMinutes(30);
	        history.setGeneratedTime(gen);
	        history.setExpirationTime(exp);
	        history.setOtp(Long.parseLong(otp1));
	        history.setOtpRef(refid1);
	        history.setDetails(details);
	        
	        historyRepo.save(history);
	        
			Email  email=new Email();
			email.setRecipient(details.getEmail());
			email.setMsgBody("otp is "+otp1+" refid is  "+refid1);
			email.setSubject("OTP");
			emailServiceImpl.sendSimpleMail(email);
			imsLogger.log("otp send successfully");
			
			return true;
		}
	}
	
	public String validateOtpAndRefId(OtpHistoryDto dto) {
		
		OtpHistory optional= historyRepo.findByOtpAndOtpRef(dto.getOtp(), dto.getOtpRef());
		imsLogger.log("otp valiadtion service initated");
		OtpHistory history=optional;
		
		if(optional==null) {
			imsLogger.log("invalid otp");
			return "invalid otp";
		}else if(optional.getGeneratedTime().isBefore(history.getExpirationTime())){
		
		
		SessionHistory history2=new SessionHistory();
		history2.setLoginTime(LocalDateTime.now());
		history2.setLogoutTime(LocalDateTime.now().plusMinutes(15));
		 String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	               + "0123456789"
	               + "abcdefghijklmnopqrstuvxyz";
	       
	        // create StringBuffer size of AlphaNumericString
	        StringBuilder sb = new StringBuilder(12);
	       
	        for (int i = 0; i < sb.capacity(); i++) {
	       
	         // generate a random number between
	         // 0 to AlphaNumericString variable length
	         int index
	          = (int)(AlphaNumericString.length()
	            * Math.random());
	       
	         // add Character one by one in end of sb
	         sb.append(AlphaNumericString
	            .charAt(index));
	        }
	       String sessionId= sb.toString();
	       history2.setSessionId(sessionId);
	       history2.setDetails(history.getDetails());
		   repo.save(history2);
		   UserDetails details=new UserDetails();
		   details.setPassword(history.getDetails().getPassword());
		   details.setUserName(history.getDetails().getUserName());
		   details.setEmail(history.getDetails().getEmail());
		   Set<UserType> roles=  history.getDetails().getRoles();
		   details.setRoles(roles);
		  historyRepo.delete(history);
		  imsLogger.log("otp valiadted succssefully");
		  return "otp validated successfully"+"/n"+" username is : "+details.getUserName()+
				  "/n"+"email is : "+details.getEmail()+"/n"+"role is "+details.getRoles();
		  
		  
		}else {
			imsLogger.log("otp session expired");
			 historyRepo.delete(history);
			return "otp session expired";
		}
	}
	
	public void setAll(ImsLogger logger) {
		this.imsLogger = logger;
	}

}
