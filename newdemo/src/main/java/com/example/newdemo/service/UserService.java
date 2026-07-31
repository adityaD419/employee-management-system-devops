package com.example.newdemo.service;

import java.time.LocalDate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.newdemo.model.AttendanceEntity;

import com.example.newdemo.model.CompanyStatics;
import com.example.newdemo.model.UserEntity;
import com.example.newdemo.repository.AttendanceRepository;
import com.example.newdemo.repository.CompanyStaticsRepository;
import com.example.newdemo.repository.UserRepository;

import com.example.newdemo.helpEntity.AttendanceSummaryDTO;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AttendanceRepository attendanceRepository;
	

//    public UserEntity updateUserStatus(Long id, String status) {
//        UserEntity user = userRepository.findById(id)
//            .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        if (!status.equals("Active") && !status.equals("Inactive")) {
//            throw new IllegalArgumentException("Invalid status value");
//        }
//
//        user.setStatus(status);
//        return userRepository.save(user);
//    }

//    public void performOperationForActiveUser(Long id) {
//        UserEntity user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " does not exist."));
//        
//        if (!user.getStatus().equals("Active")) {
//            throw new IllegalStateException("Operation not allowed for inactive users.");
//        }
//        
//        // Perform the operation for active users
//    }
	public Map<String, Object> getUsersWithAttendance(LocalDate date) {
	    if (date == null) {
	        date = LocalDate.now(); // Default to today's date if no date is provided
	    }

	    List<Object[]> results = userRepository.fetchAllUsersWithAttendance(date);

	    long totalCount = userRepository.countAllUsers();
	    long attendanceCount = attendanceRepository.countAttendanceByDate(date);

	    // Convert results to a list of maps
	    List<Map<String, Object>> usersList = results.stream().map(record -> {
	        Map<String, Object> map = new LinkedHashMap<>();
	        map.put("userId", record[0]);
	        map.put("userName", record[1]);
	        map.put("userRole", record[2]);

	        // Ensure 'attType' and 'hours' are properly mapped and formatted
	        String attType = (record[3] != null) ? (String) record[3] : ""; // 'half day' or 'full day' or ""
	        String hours = (record[4] != null) ? (String) record[4] : ""; // Hours value or ""

	        // Ensure the correct display of attendance type and hours
	        map.put("attType", attType);
	        map.put("hours", hours);

	        map.put("attendanceCreatedAt", (record[5] != null) ? record[5].toString() : "");
	        map.put("attendanceUpdatedAt", (record[6] != null) ? record[6].toString() : "");

	        return map;
	    }).collect(Collectors.toList());

	    // Prepare the response
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("totalCount", totalCount);
	    response.put("attendanceCount", attendanceCount);
	    response.put("UserList", usersList);

	    return response;
	}


	@Autowired
	private CompanyStaticsRepository companyStaticsRepository;

	public List<CompanyStatics> getAllCompanyStatics() {
		return companyStaticsRepository.findAll();
	}

	public AttendanceSummaryDTO getAttendanceSummary(Long userId, int month, int year) {
	    // Define the start and end dates for the month
	    LocalDate startDate = LocalDate.of(year, month, 1);
	    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

	    // Fetch attendance records for the given user and date range
	    List<AttendanceEntity> attendanceList = attendanceRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);

	    int fullDays = 0;
	    int halfDays = 0;
	    int individualHours = 0;
	    
	    // Calculate full days, half days, and individual hours
	    for (AttendanceEntity attendance : attendanceList) {
	        if ("full day".equalsIgnoreCase(attendance.getAttType())) {
	            fullDays++;
	        } else if ("half day".equalsIgnoreCase(attendance.getAttType())) {
	            halfDays++;
	        }

	        if (attendance.getHours() != null && !attendance.getHours().isEmpty()) {
	            individualHours += Integer.parseInt(attendance.getHours());
	        }
	    }

	    // Calculate total hours: full days * 9 + half days * 5 + individual hours
	    int totalHours = (fullDays * 9) + (halfDays * 5) + individualHours;

	    // Return the attendance summary
	    return new AttendanceSummaryDTO(fullDays, halfDays, individualHours, totalHours);
	}
}


