package com.ims.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OtpHistoryDto {

	private int id;
	
	private long otp;
	
	private String otpRef;

	private LocalDateTime generatedTime;
	
	private LocalDateTime expirationTime;
	
	private UserDetailsDto details;
}
