package com.example.newdemo.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.newdemo.model.UserEntity;

public interface UserRepository  extends JpaRepository<UserEntity,Long> {
	//yeh isliye hai taaki email wali ko find kr ske
	
	Optional<UserEntity> findByEmail(String email);

	@Query("SELECT u.id AS userId, u.name AS userName, u.role AS userRole, " +
		       "a.attType AS attType, a.hours AS hours, " +
		       "a.createdAt AS attendanceCreatedAt, a.updatedAt AS attendanceUpdatedAt " +
		       "FROM UserEntity u " +
		       "LEFT JOIN u.attendances a ON a.createdAt = :date " +
		       "ORDER BY u.id ASC")
		List<Object[]> fetchAllUsersWithAttendance(@Param("date") LocalDate date);

    @Query("SELECT COUNT(u) FROM UserEntity u")
    long countAllUsers();
    boolean existsByEmail(String email);
    List<UserEntity> findByStatus(String status);
   
   


}
