package com.ims.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ims.utils.Utils;

public class ImsLogger {

	private Logger logger;
	private String refNo;
	
	public <T> ImsLogger(Class<T> classes){
		logger=LogManager.getLogger(classes);
		refNo=Utils.getRandomNumberString(9);
	}
	
	public void log(Object o) {
		logger.info("["+refNo+"] "+o.toString());
	}
	
}
