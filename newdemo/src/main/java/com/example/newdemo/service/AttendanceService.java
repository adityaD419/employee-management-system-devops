package com.example.newdemo.service;

import com.example.newdemo.helpEntity.ResponseAdmin;

import com.example.newdemo.model.AttendanceEntity;
import com.example.newdemo.model.Rate;
import com.example.newdemo.model.UserEntity;
import com.example.newdemo.repository.AttendanceRepository;
import com.example.newdemo.repository.RateRepository;
import com.example.newdemo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RateRepository rateRepository;

	public void markAttendance(Long userId, String hours, String attType, LocalDate createdAt) {
		// Determine the attendance date, use provided or default to today's date
		LocalDate attendanceDate = (createdAt != null) ? createdAt : LocalDate.now();

		// Print the attendance date to the console
		System.out.println("Attendance Date: " + attendanceDate);
		// Check if the attendance date is in the future
		if (attendanceDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("You cannot mark attendance for a future date.");
		}

		if ((hours == null || hours.isEmpty()) && (attType == null || attType.isEmpty())) {
			throw new IllegalArgumentException("Either hours or attType must be provided.");
		}

		if (!(hours == null || hours.isEmpty()) && !(attType == null || attType.isEmpty())) {
			throw new IllegalArgumentException("Only one of hours or attType should be provided.");
		}

		if (attType != null && !attType.isEmpty()) {
			if (!attType.equals("half day") && !attType.equals("full day")) {
				throw new IllegalArgumentException("attType must be either 'half day' or 'full day'.");
			}
		}

		if (hours != null && !hours.isEmpty()) {
			try {
				Integer.parseInt(hours); // Validate hours as an integer
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("hours must be a valid number.");
			}
		}

		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

		if ("Inactive".equalsIgnoreCase(user.getStatus())) {
			throw new IllegalStateException("Attendance cannot be marked for inactive users.");
		}

		Optional<AttendanceEntity> existingAttendanceOpt = attendanceRepository.findByUserIdAndCreatedAt(userId,
				attendanceDate);

		if (existingAttendanceOpt.isPresent()) {
			AttendanceEntity existingAttendance = existingAttendanceOpt.get();

			if (hours != null && !hours.isEmpty()) {
				existingAttendance.setHours(hours);
				existingAttendance.setAttType("");
			} else if (attType != null && !attType.isEmpty()) {
				existingAttendance.setAttType(attType);
				existingAttendance.setHours("");
			}

			existingAttendance.setUpdatedAt(LocalDate.now());
			existingAttendance.setStatus("Updated");
			attendanceRepository.save(existingAttendance);
		} else {
			AttendanceEntity attendance = new AttendanceEntity();
			attendance.setUser(user);
			attendance.setCreatedAt(attendanceDate); // Use the provided or default date
			attendance.setUpdatedAt(LocalDate.now());

			if (hours != null && !hours.isEmpty()) {
				attendance.setHours(hours);
				attendance.setAttType("");
			} else if (attType != null && !attType.isEmpty()) {
				attendance.setAttType(attType);
				attendance.setHours("");
			}

			attendance.setStatus("Created");
			attendanceRepository.save(attendance);
		}
	}
	
}
