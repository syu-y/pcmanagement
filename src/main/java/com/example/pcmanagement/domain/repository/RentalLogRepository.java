package com.example.pcmanagement.domain.repository;

import java.util.List;

import com.example.pcmanagement.domain.model.RentalLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalLogRepository extends JpaRepository<RentalLog, String> {
    @Query(value="select * from history where END_DATE IS NULL", nativeQuery = true)
    public List<RentalLog> findNotReturnLog();
}
