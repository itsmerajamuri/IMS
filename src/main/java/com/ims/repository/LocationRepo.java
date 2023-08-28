package com.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.entity.Location;

public interface LocationRepo extends JpaRepository<Location, Integer>{

}
