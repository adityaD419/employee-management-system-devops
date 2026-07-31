package com.example.newdemo.repository;

import com.example.newdemo.model.AttendanceEntity;
import com.example.newdemo.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    
    // Method to find overlapping rates
	@Query("SELECT r FROM Rate r WHERE r.user.id = :userId AND r.effectiveFrom <= :effectiveTill AND r.effectiveTill >= :effectiveFrom")
    List<Rate> findOverlappingRateByUser(@Param("effectiveFrom") LocalDate effectiveFrom, 
                                         @Param("effectiveTill") LocalDate effectiveTill,
                                         @Param("userId") Long userId);

    @Query("SELECT r FROM Rate r WHERE r.user.id = :userId AND r.effectiveFrom <= :endDate AND r.effectiveTill >= :startDate")
    List<Rate> findByEffectiveRangeAndUser(@Param("userId") Long userId,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);
    @Query("SELECT r FROM Rate r WHERE r.effectiveFrom <= :effectiveTill AND r.effectiveTill >= :effectiveFrom")
    List<Rate> findByEffectiveFromLessThanEqualAndEffectiveTillGreaterThanEqual(
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTill") LocalDate effectiveTill);

    @Query("SELECT r FROM Rate r WHERE r.user.id = :userId AND r.effectiveFrom <= :endDate AND r.effectiveTill >= :startDate")
    List<Rate> findByUserIdAndEffectiveRange(@Param("userId") Long userId,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);
    @Query("SELECT r FROM Rate r WHERE r.user.id = :userId AND r.effectiveFrom <= :endDate AND r.effectiveTill >= :startDate")
    List<Rate> findRatesByUserAndMonth(@Param("userId") Long userId,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);
    List<Rate> findByUserId(Long userId);
}
