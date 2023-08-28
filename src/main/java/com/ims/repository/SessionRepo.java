package com.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.entity.SessionHistory;

public interface SessionRepo extends JpaRepository<SessionHistory, Integer>{

}
