package com.ims.response;

import java.util.List;

import com.ims.dto.UserTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTypeApiResponse {
	
	private int responseCode;
	
	private List<UserTypeDto> getUserTypes;

}
