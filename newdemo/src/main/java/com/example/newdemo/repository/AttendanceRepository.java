//package com.example.newdemo.repository;
//
//import com.example.newdemo.model.AttendanceEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
//    // Custom query to find attendance by userId and date (used in markAttendance method)
//    Optional<AttendanceEntity> findByUserIdAndCreatedAt(Long userId, LocalDate createdAt);
//    @Query("SELECT COUNT(a.id) FROM AttendanceEntity a WHERE a.createdAt = :date")
//    long countAttendanceByDate(@Param("date") LocalDate date);
//    
//    @Query("SELECT a FROM AttendanceEntity a WHERE a.user.id = :userId AND FUNCTION('MONTH', a.createdAt) = :month AND FUNCTION('YEAR', a.createdAt) = :year")
//    List<AttendanceEntity> findAttendanceByUserAndMonth(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);
//    
//   // List<AttendanceEntity> findByUserIdAndCreatedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);
//    List<AttendanceEntity> findByUserIdAndCreatedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);
//}
package com.example.newdemo.repository;


import com.example.newdemo.model.AttendanceEntity;
import com.example.newdemo.model.Rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    // Find attendance by userId and date
    Optional<AttendanceEntity> findByUserIdAndCreatedAt(Long userId, LocalDate createdAt);
    
    // Count attendance on a particular date
    @Query("SELECT COUNT(a.id) FROM AttendanceEntity a WHERE a.createdAt = :date")
    long countAttendanceByDate(@Param("date") LocalDate date);
    
    // Find attendance by user and specific month and year
    @Query("SELECT a FROM AttendanceEntity a WHERE a.user.id = :userId AND FUNCTION('MONTH', a.createdAt) = :month AND FUNCTION('YEAR', a.createdAt) = :year")
    List<AttendanceEntity> findAttendanceByUserAndMonth(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

    // Find attendance between specific dates
    List<AttendanceEntity> findByUserIdAndCreatedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);

}