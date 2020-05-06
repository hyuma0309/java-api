package com.teamlab.engineering.restfulapi.repository;

import com.teamlab.engineering.restfulapi.entitiy.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {}
