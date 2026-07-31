package com.example.newdemo.service;

import com.example.newdemo.helpEntity.MonthlyDTO;

import com.example.newdemo.model.AttendanceEntity;
import com.example.newdemo.model.Rate;
import com.example.newdemo.model.UserEntity;
import com.example.newdemo.repository.AttendanceRepository;
import com.example.newdemo.repository.RateRepository;
import com.example.newdemo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
	@Autowired
	private UserRepository userRepository;
	public String createOrUpdateRate(Double rate, LocalDate effectiveFrom, LocalDate effectiveTill, Long userId) {
	    // Ensure the effectiveFrom and effectiveTill are in the same month and year
	    if (effectiveFrom.getMonth() != effectiveTill.getMonth() || effectiveFrom.getYear() != effectiveTill.getYear()) {
	        return "Effective dates must be within the same month and year.";
	    }

	    // Find the user by ID
	    UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("User not found"));

	    // Find existing rates that overlap with the new rate range for this user
	    List<Rate> overlappingRates = rateRepository.findOverlappingRateByUser(effectiveFrom, effectiveTill, userId);

	    // Merge or update existing rates
	    if (!overlappingRates.isEmpty()) {
	        Rate existingRate = overlappingRates.get(0);  // Assume there's only one rate per month
	        // Adjust the effective dates and rate for the existing record
	        existingRate.setEffectiveFrom(effectiveFrom.isBefore(existingRate.getEffectiveFrom()) ? effectiveFrom : existingRate.getEffectiveFrom());
	        existingRate.setEffectiveTill(effectiveTill.isAfter(existingRate.getEffectiveTill()) ? effectiveTill : existingRate.getEffectiveTill());
	        existingRate.setRate(rate);
	        rateRepository.save(existingRate);
	        return "Rate period updated successfully.";
	    }

	    // If no existing rate is found, create a new one
	    Rate newRate = new Rate();
	    newRate.setRate(rate);
	    newRate.setEffectiveFrom(effectiveFrom);
	    newRate.setEffectiveTill(effectiveTill);
	    newRate.setUser(user);
	    rateRepository.save(newRate);
	    return "Rate created successfully.";
	}

	public double calculateTotalRating(Long userId, int month, int year) {
	    // Determine the start and end dates for the specified month
	    LocalDate startDate = LocalDate.of(year, month, 1);
	    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

	    // Retrieve all attendance records for the user within the specified month
	    List<AttendanceEntity> attendanceList = attendanceRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);

	    // Calculate total hours based on the attendance records
	    int totalHours = 0;
	    for (AttendanceEntity attendance : attendanceList) {
	        if (attendance.getHours() != null && !attendance.getHours().isEmpty()) {
	            totalHours += Integer.parseInt(attendance.getHours());
	        } else {
	            // Only consider attendance type if hours are not provided
	            if ("full day".equalsIgnoreCase(attendance.getAttType())) {
	                totalHours += 9;
	            } else if ("half day".equalsIgnoreCase(attendance.getAttType())) {
	                totalHours += 5;
	            }
	        }
	    }

	    // Fetch the applicable rate for the specified month and year
	    List<Rate> applicableRates = rateRepository.findByEffectiveRangeAndUser(userId, startDate, endDate);

	    if (applicableRates.isEmpty()) {
	        throw new IllegalArgumentException("No applicable rate found for the specified period.");
	    }

	    // Assume a single rate for simplicity; adjust logic if there are multiple rates
	    Rate applicableRate = applicableRates.get(0);

	    // Calculate and return the total salary
	    return totalHours * applicableRate.getRate();
	}
//	 public List<MonthlyDTO> getRatesByUser(Long userId) {
//	        // Find the user by ID
//	        UserEntity user = userRepository.findById(userId)
//	                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//	        // Fetch all rates for the specified user
//	        List<Rate> rates = rateRepository.findByUserId(userId);
//
//	        // Convert rates to a list of MonthlyRateDTO
//	        return rates.stream()
//	                .map(rate -> new MonthlyDTO(
//	                        rate.getEffectiveFrom().getMonth().toString(),  // Month name
//	                        rate.getRate()))
//	                .collect(Collectors.toList());
//	    }

	public List<MonthlyDTO> getRatesByUserAndYear(Long userId, Integer year) {
	    // Validate the year parameter
	    if (year == null) {
	        throw new IllegalArgumentException("Year parameter is required.");
	    }

	    // Find the user by ID
	    UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("User not found"));

	    // Determine the start and end dates for the specified year
	    LocalDate startOfYear = LocalDate.of(year, 1, 1);
	    LocalDate endOfYear = LocalDate.of(year, 12, 31);

	    // Fetch all rates for the specified user and year
	    List<Rate> rates = rateRepository.findByUserIdAndEffectiveRange(userId, startOfYear, endOfYear);

	    // Convert rates to a list of MonthlyDTO
	    return rates.stream()
	            .map(rate -> new MonthlyDTO(
	                    rate.getEffectiveFrom().getMonth().toString(),  // Month name
	                    rate.getRate()))
	            .collect(Collectors.toList());
	}



}
