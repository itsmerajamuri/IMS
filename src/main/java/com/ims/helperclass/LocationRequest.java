package com.ims.helperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationRequest {

	
	private String location;
	
	private String userName;
	
	private String userType;
}
