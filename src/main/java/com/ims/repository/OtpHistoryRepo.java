package com.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ims.entity.OtpHistory;

@Repository
public interface OtpHistoryRepo extends JpaRepository<OtpHistory, Integer>{

	OtpHistory findByOtpAndOtpRef(long otp,String otRefId);
}
