package com.example.newdemo.Controller;

import com.example.newdemo.exception.ValidationUtil.UserNotFoundException;
import com.example.newdemo.helpEntity.AttendanceRequest;

import com.example.newdemo.helpEntity.LoginRequest;
import com.example.newdemo.helpEntity.MonthlyDTO;
import com.example.newdemo.helpEntity.RateDTO;
import com.example.newdemo.helpEntity.ResponseAdmin;
import com.example.newdemo.helpEntity.ResponseUser;
import com.example.newdemo.helpEntity.StatusRequest;

import com.example.newdemo.model.AdminEntity;
import com.example.newdemo.model.CompanyStatics;

import com.example.newdemo.model.UserEntity;
import com.example.newdemo.repository.UserRepository;
import com.example.newdemo.service.AdminService;
import com.example.newdemo.service.AttendanceService;
import com.example.newdemo.service.RateService;
import com.example.newdemo.service.TokenService;
import com.example.newdemo.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.newdemo.helpEntity.AttendanceSummaryDTO;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private RateService rateService;

	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/newuser")
	public ResponseUser createNewUser(@Valid @RequestBody UserEntity userEntity) {
		return adminService.insertNewUser(userEntity);
	}

	@PostMapping("/loginuser")
	public ResponseUser loginUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("Hi Rahul - Starting loginUser method");

		// Authenticate user
		ResponseUser responseUser = adminService.loginuser(loginRequest.getEmail(), loginRequest.getPassword());

		if (responseUser != null) {
			System.out.println("User authenticated successfully.");
		} else {
			System.out.println("User authentication failed.");
		}

		return responseUser;
	}

//	@PutMapping("/update/{id}")
//	public ResponseEntity<Object> updateEntity(@PathVariable Long id, @RequestBody UserEntity updatedEntity) {
//		try {
//			String role = updatedEntity.getRole(); // Ensure role is provided in the request
//			ResponseAdmin response = adminService.updateDetails(id, updatedEntity, role);
//			return ResponseEntity.ok(response);
//		} catch (IllegalArgumentException ex) {
//			return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "/admin/update/" + id);
//		} catch (RuntimeException ex) {
//			return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "/admin/update/" + id);
//		}
//	}
//	@PutMapping("/update/{id}")
//	public ResponseEntity<Object> updateEntity(
//	        @PathVariable Long id,
//	        @RequestBody UserEntity updatedEntity,
//	        @RequestHeader("Authorization") String authorizationHeader) {
//
//	    // Extract the token from the Authorization header
//	    String token = authorizationHeader.replace("Bearer ", "");
//
//	    // Validate the token
//	    if (!tokenService.validateToken(token)) {
//	        // Token is invalid or expired, return 401 Unauthorized
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
//	    }
//
//	    try {
//	        // Token is valid, proceed to update the user
//	        String roles = updatedEntity.getRole();  // Assuming roles are passed in as a set
//	        if (roles == null || roles.isEmpty()) {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No role provided");
//	        }
//
//	        // Update the user based on the roles
//	        ResponseAdmin response = adminService.updateDetails(id, updatedEntity, roles);
//	        return ResponseEntity.ok(response);
//
//	    } catch (IllegalArgumentException ex) {
//	        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "/admin/update/" + id);
//	    } catch (RuntimeException ex) {
//	        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "/admin/update/" + id);
//	    }
//	}

//@GetMapping("/{id}")
//	public ResponseEntity<Object> getUserById(@PathVariable Long id) {
//		try {
//			Optional<ResponseAdmin> user = adminService.getUserById(id);
//			if (user.isPresent()) {
//				return ResponseEntity.ok(user.get());
//			} else {
//				return buildErrorResponse(HttpStatus.NOT_FOUND, "User not found", "/admin/" + id);
//			}
//		} catch (RuntimeException ex) {
//			return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "/admin/" + id);
//		}
//	}
//	@GetMapping("/{id}")
//	public ResponseEntity<Object> getUserById(@PathVariable Long id,
//	                                           @RequestHeader("Authorization") String authorizationHeader) {
//	    // Extract the token from the Authorization header
//	    String token = authorizationHeader.replace("Bearer ", "");
//
//	    // Validate the token
//	    if (tokenService.validateToken(token)) {
//	        // Token is valid, proceed to get user by ID
//	        try {
//	        	Optional<ResponseAdmin> user = adminService.getUserById(id);
//	            if (user.isPresent()) {
//	                return ResponseEntity.ok(user.get());
//	            } else {
//	                return buildErrorResponse(HttpStatus.NOT_FOUND, "User not found", "/admin/" + id);
//	            }
//	        } catch (RuntimeException ex) {
//	            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "/admin/" + id);
//	        }
//	    } else {
//	        // Token is invalid, return an unauthorized response
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
//	    }
//	}

//	 @PutMapping("/update-user-status/{id}")
//	    public ResponseEntity<String> updateUserStatus(@PathVariable Long id, @RequestBody StatusRequest statusRequest) {
//	        try {
//	           // UserEntity updatedUser = userService.updateUserStatus(id, statusRequest.getStatus());
//	            return ResponseEntity.ok("User status updated successfully to " + statusRequest.getStatus());
//	        } catch (IllegalArgumentException e) {
//	            return ResponseEntity.badRequest().body(e.getMessage());
//	        }
//	    }
//	    @PostMapping("/perform-operation/{id}")
//	    public ResponseEntity<String> performOperationForActiveUser(@PathVariable Long id) {
//	        try {
//	            userService.performOperationForActiveUser(id);
//	            return ResponseEntity.ok("Operation performed successfully.");
//	        } catch (IllegalStateException e) {
//	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//	        }
//	    }
//	@GetMapping("/allusers")
//	public ResponseEntity<List<ResponseAdmin>> getAllUsers() {
//		
//		List<ResponseAdmin> users = adminService.getAllUsers();
//		return ResponseEntity.ok(users);
//	}
//
//	 @Autowired
//	 private UserRepository userRepository;
////	 @GetMapping("/allusers")
////	 public ResponseEntity<?> getAllUsers(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, HttpServletRequest request) {
////	     if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
////	         return buildErrorResponse(HttpStatus.BAD_REQUEST, "Token is missing or malformed", request.getRequestURI());
////	     }
////
////	     String token = authorizationHeader.replace("Bearer ", "");
////
////	     try {
////	         if (tokenService.validateToken(token)) {
////	             List<ResponseAdmin> users = adminService.getAllUsers();
////	             return ResponseEntity.ok(users);
////	         } else {
////	             return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Token is invalid", request.getRequestURI());
////	         }
////	     } catch (ExpiredJwtException e) {
////	         return buildErrorResponse(HttpStatus.UNAUTHORIZED, "JWT Token has expired", request.getRequestURI());
////	     } catch (MalformedJwtException e) {
////	         return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Malformed JWT Token", request.getRequestURI());
////	     } catch (SignatureException e) {
////	         return buildErrorResponse(HttpStatus.UNAUTHORIZED, "JWT Token signature is invalid", request.getRequestURI());
////	     } catch (IllegalArgumentException e) {
////	         return buildErrorResponse(HttpStatus.UNAUTHORIZED, "JWT claims string is empty", request.getRequestURI());
////	     } catch (Exception e) {
////	         return buildErrorResponse(HttpStatus.UNAUTHORIZED, "JWT Token validation failed", request.getRequestURI());
////	     }
////	 }
//
//
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
//		try {
//			String message = adminService.deleteUserById(id);
//			return buildSuccessResponse(HttpStatus.OK, message, "/admin/delete/" + id);
//		} catch (UserNotFoundException ex) {
//			return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "/admin/delete/" + id);
//		}
//	}
//
//	@DeleteMapping("/delete/all")
//	public ResponseEntity<Object> deleteAllUsers() {
//		String message = adminService.deleteAllUsers();
//		return buildSuccessResponse(HttpStatus.OK, message, "/admin/delete/all");
//	}
//	@PostMapping("/mark")
//	public ResponseEntity<String> markAttendance(@RequestBody AttendanceRequest request) {
//	    // Log the received parameters for debugging
//	    System.out.println("Received userId: " + request.getUserId());
//	    System.out.println("Received hours: " + request.getHours());
//	    System.out.println("Received attType: " + request.getAttType());
//	    System.out.println("Received date: " + request.getCreatedAt());
//
//	    // Extract parameters from the request
//	    Long userId = request.getUserId();
//	    String hours = request.getHours();
//	    String attType = request.getAttType();
//	    LocalDate createdAt = request.getCreatedAt();
//
//	    // Validate userId
//	    if (userId == null) {
//	        return ResponseEntity.badRequest().body("userId is required");
//	    }
//
//	    try {
//	        // Call the service method
//	        attendanceService.markAttendance(userId, hours, attType, createdAt);
//	        return ResponseEntity.ok("Attendance marked successfully.");
//	    } catch (IllegalArgumentException | IllegalStateException e) {
//	        return ResponseEntity.badRequest().body(e.getMessage());
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
//	    }
//	}
//
//
//
//
//
//	@Autowired
//	private UserService userService;
//
//	@GetMapping("/with-attendance")
//	public Map<String, Object> getAllUsersWithAttendance(@RequestParam(required = false) LocalDate date) {
//		return userService.getUsersWithAttendance(date);
//	}
//
//	@GetMapping("/companystatics")
//	public List<CompanyStatics> fetchCompanyStatics() {
//		return userService.getAllCompanyStatics();
//	}
//
//	@GetMapping("/summary/{userId}/{month}/{year}")
//	public ResponseEntity<AttendanceSummaryDTO> getAttendanceSummary(@PathVariable Long userId, @PathVariable int month,
//			@PathVariable int year) {
//		AttendanceSummaryDTO summary = userService.getAttendanceSummary(userId, month, year);
//		return ResponseEntity.ok(summary);
//	}
//	
//	
//	@PostMapping("/createrate")
//    public ResponseEntity<String> createOrUpdateRate(@RequestBody RateDTO rateDTO) {
//        try {
//            String result = rateService.createOrUpdateRate(rateDTO.getRate(), rateDTO.getEffectiveFrom(), rateDTO.getEffectiveTill(), rateDTO.getUserId());
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//	
//
//	@GetMapping("/calculateTotalRating")
//    public ResponseEntity<Double> calculateTotalRating(@RequestParam Long userId, 
//                                                       @RequestParam int month, 
//                                                       @RequestParam int year) {
//        try {
//            double totalRating = rateService.calculateTotalRating(userId, month, year);
//            return new ResponseEntity<>(totalRating, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
////	 @GetMapping("/user/{userId}")
////	    public ResponseEntity<List<MonthlyDTO>> getRatesByUser(@PathVariable Long userId) {
////	        List<MonthlyDTO> ratesByMonth = rateService.getRatesByUser(userId);
////	        return ResponseEntity.ok(ratesByMonth);
////	    }
//	@GetMapping("/{userId}/year/{year}")
//	public ResponseEntity<?> getRatesByUserAndYear(@PathVariable Long userId, @PathVariable Integer year) {
//	    try {
//	        List<MonthlyDTO> rates = rateService.getRatesByUserAndYear(userId, year);
//	        if (rates.isEmpty()) {
//	            return ResponseEntity.ok(Collections.singletonMap("message", "Rate is not marked for this user for the year " + year + "."));
//	        }
//	        return ResponseEntity.ok(rates);
//	    } catch (IllegalArgumentException e) {
//	        return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An unexpected error occurred"));
//	    }
//	}
//	@Autowired
//    private TokenService tokenService;
//	// @PostMapping("/generatetoken")
//
//	@GetMapping("/generatetoken")
//	public ResponseEntity<String> generateToken(@RequestParam Long userId, @RequestParam String email) {
//	    try {
//	        // Attempt to convert userId to Long
////	        Long parsedUserId = Long.parseLong(userId);
//	        
//	        System.out.println("UserId: " + userId);
//	        System.out.println("Email: " + email);
//	        
//	        // Generate token with valid parsed userId
//	        String token = tokenService.generateAndSaveToken(userId, email);
//	        return ResponseEntity.ok(token);
//	    } catch (NumberFormatException e) {
//	        // Handle invalid userId format
//	        return ResponseEntity.badRequest().body("Invalid userId. It must be a number.");
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the token.");
//	    }
//	}
//
////    @Autowired
////    private JwtUtil jwtUtil;
////    @GetMapping("/verifytoken")
////    public ResponseEntity<String> verifyToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
////        try {
////            // Remove "Bearer " prefix from the token
////            if (token.startsWith("Bearer ")) {
////                token = token.substring(7);
////            }
////
////            // Extract claims and verify token
////            String email = jwtUtil.extractEmail(token);
////            if (jwtUtil.ValidateToken(token, email)) {
////                return ResponseEntity.ok("Token is valid for user: " + email);
////            } else {
////                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired.");
////            }
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token verification failed: " + e.getMessage());
////        }
////    }
//    // Endpoint to generate and save JWT token
//   
//	// Method to build error responses
//	private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, String path) {
//		Map<String, Object> errorDetails = new HashMap<>();
//		errorDetails.put("status", status.value());
//		errorDetails.put("message", message);
//		errorDetails.put("path", path);
//		return ResponseEntity.status(status).body(errorDetails);
//	}
//
//	private ResponseEntity<Object> buildSuccessResponse(HttpStatus status, String message, Object data) {
//		Map<String, Object> successDetails = new HashMap<>();
//		successDetails.put("status", status.value());
//		successDetails.put("message", message);
//		successDetails.put("data", data);
//		return ResponseEntity.status(status).body(successDetails);
//	}

}
