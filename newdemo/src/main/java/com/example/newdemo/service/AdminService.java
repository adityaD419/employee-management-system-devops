package com.example.newdemo.service;

import com.example.newdemo.exception.ValidationUtil;
import com.example.newdemo.configuration.JwtUtil;
import com.example.newdemo.configuration.PasswordUtility;
import com.example.newdemo.helpEntity.ResponseAdmin;
import com.example.newdemo.helpEntity.ResponseUser;
import com.example.newdemo.model.AdminEntity;
import com.example.newdemo.model.UserEntity;
import com.example.newdemo.repository.AdminRepository;
import com.example.newdemo.repository.UserRepository;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ModelMapper modelMapper;
	public ResponseAdmin insertNewAdmin(AdminEntity newadmin) {
	    validateEmail(newadmin.getEmail());
	    String formattedName = validateAndFormatName(newadmin.getName());
	    newadmin.setName(formattedName);
	    validatePassword(newadmin.getPassword());

	    // Check if the admin email already exists
	    if (adminRepository.findByEmail(newadmin.getEmail()).isPresent()) {
	        throw new ValidationUtil.UserAlreadyExistsException("Admin with this email already exists");
	    }

	    // Check if the user email already exists
	    if (userRepository.findByEmail(newadmin.getEmail()).isPresent()) {
	        throw new ValidationUtil.UserAlreadyExistsException("User with this email already exists");
	    }

	    newadmin.setPassword(PasswordUtility.hashPassword(newadmin.getPassword()));
	    AdminEntity createdAdmin = adminRepository.save(newadmin);
	    ResponseAdmin response = modelMapper.map(createdAdmin, ResponseAdmin.class);
	    response.setMessage("Successfully registered");

	    return response;
	}
	private void validateEmail(String email) {
	    if (email == null || email.trim().isEmpty()) {
	        throw new ValidationUtil.InvalidEmailException("Email cannot be empty");
	    }
	    // Additional check for a valid email format can be added here if needed
	}
	private String validateAndFormatName(String name) {
	    if (name == null || name.trim().isEmpty()) {
	        throw new ValidationUtil.InvalidEmailException("Name cannot be empty");
	    }
	    String formattedName = formatName(name.trim());
	    if (formattedName == null) {
	        throw new ValidationUtil.InvalidNameFormatException(
	                "Name must be in the format 'FirstName LastName' with exactly one space between names");
	    }
	    return formattedName;
	}

	private void validatePassword(String password) {
	    String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	    if (password == null || !Pattern.matches(PASSWORD_PATTERN, password)) {
	        throw new ValidationUtil.InvalidPasswordException(
	                "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
	    }
	}

	public ResponseUser insertNewUser(UserEntity user) {
	    validateEmail(user.getEmail());
	    String formattedName = validateAndFormatName(user.getName());
	    user.setName(formattedName);
	    validatePassword(user.getPassword());

	    // Check if the user email already exists
	    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	        throw new ValidationUtil.UserAlreadyExistsException("User with this email already exists");
	    }

	    // Check if the admin email already exists
	    if (adminRepository.findByEmail(user.getEmail()).isPresent()) {
	        throw new ValidationUtil.UserAlreadyExistsException("Admin with this email already exists");
	    }

	    user.setPassword(PasswordUtility.hashPassword(user.getPassword()));
	    UserEntity createdUser = userRepository.save(user);
	    ResponseUser response = modelMapper.map(createdUser, ResponseUser.class);
	    response.setMessage("Successfully registered");

	    return response;
	}

	// Validation Methods remain unchanged


//	public ResponseUser loginuser(String email, String password) {
//		Optional<UserEntity> optionalAdmin = userRepository.findByEmail(email);
//		if (optionalAdmin.isPresent()) {
//			UserEntity user = optionalAdmin.get();
//			if (PasswordUtility.checkPassword(password, user.getPassword())) {
//				return modelMapper.map(user, ResponseUser.class);
//			} else {
//				throw new ValidationUtil.InvalidPasswordException("Invalid password");
//			}
//		} else {
//			throw new ValidationUtil.UserNotFoundException("User not found");
//		}
//	}
	@Autowired
    private TokenService jwtUtil;
	public ResponseAdmin loginAdmin(String email, String password) {
	//	System.out.println("jjfhjgjhghjgjhgjgk");
        Optional<AdminEntity> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
           AdminEntity admin = optionalAdmin.get();
            if (PasswordUtility.checkPassword(password, admin.getPassword())) {
                // Generate the JWT token
                String token = jwtUtil.generateAndSaveToken(admin.getId(), admin.getEmail());
                 System.out.println(token);
                // Map UserEntity to ResponseAdmin and set the token
                ResponseAdmin responseAdmin = modelMapper.map(admin, ResponseAdmin.class);
                responseAdmin.setToken(token);
                
                return responseAdmin;
            } else {
                throw new ValidationUtil.InvalidPasswordException("Invalid password");
            }
        } else {
            throw new ValidationUtil.UserNotFoundException("Admin not found");
        }
    }
	public ResponseUser loginuser(String email, String password) {
		Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			UserEntity user = optionalUser.get();
			if (PasswordUtility.checkPassword(password, user.getPassword())) {
				return modelMapper.map(user, ResponseUser.class);
			} else {
				throw new ValidationUtil.InvalidPasswordException("Invalid password");
			}
		} else {
			throw new ValidationUtil.UserNotFoundException("User not found");
		}
	}

//	public ResponseAdmin updateDetails(Long id, UserEntity updatedEntity, String roles) {
//	    if (roles.contains("admin")) {
//	        // If the user has an admin role, perform admin-related updates
//	        return updateAdmin(id, updatedEntity);
//	    } else if (roles.contains("user")) {
//	        // If the user has a user role, perform user-related updates
//	        return updateUser(id, updatedEntity);
//	    } else {
//	        // If no valid role is found, throw an exception
//	        throw new IllegalArgumentException("Invalid role specified");
//	    }
//	}


	private ResponseAdmin updateAdmin(Long adminId, @Valid UserEntity updatedAdmin) {
		Optional<AdminEntity> optionalAdmin = adminRepository.findById(adminId);
		if (optionalAdmin.isPresent()) {
			AdminEntity admin = optionalAdmin.get();

			String name = updatedAdmin.getName();
			if (name == null || name.trim().isEmpty()) {
				throw new ValidationUtil.InvalidEmailException("Name cannot be empty");
			}
			String formattedName = formatName(name.trim());
			if (formattedName == null) {
				throw new ValidationUtil.InvalidNameFormatException(
						"Name must be in the format 'FirstName LastName' with exactly one space between names");
			}
			admin.setName(formattedName);

			String email = updatedAdmin.getEmail();
			if (email == null || email.trim().isEmpty()) {
				throw new ValidationUtil.InvalidEmailException("Email cannot be empty");
			}
			admin.setEmail(email.trim());

			admin.setRole(updatedAdmin.getRole());
			String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
			String newPassword = updatedAdmin.getPassword();

			if (newPassword != null && !newPassword.isEmpty()) {
				if (!Pattern.matches(PASSWORD_PATTERN, newPassword)) {
					throw new ValidationUtil.InvalidPasswordException(
							"Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
				}
				admin.setPassword(PasswordUtility.hashPassword(newPassword));
			}

			AdminEntity savedAdmin = adminRepository.save(admin);
			return modelMapper.map(savedAdmin, ResponseAdmin.class);
		} else {
			throw new ValidationUtil.UserNotFoundException("Admin not found");
		}
	}
	
	public ResponseUser updateUser(Long userId, @Valid UserEntity updatedUser) {
	    // Fetch user from repository
	    Optional<UserEntity> optionalUser = userRepository.findById(userId);
	    if (!optionalUser.isPresent()) {
	        throw new ValidationUtil.UserNotFoundException("User not found");
	    }

	    UserEntity user = optionalUser.get();

	    // Update and validate user details
	    String name = updatedUser.getName();
	    if (name == null || name.trim().isEmpty()) {
	        throw new ValidationUtil.InvalidEmailException("Name cannot be empty");
	    }
	    String formattedName = formatName(name.trim());
	    if (formattedName == null) {
	        throw new ValidationUtil.InvalidNameFormatException(
	                "Name must be in the format 'FirstName LastName' with exactly one space between names");
	    }
	    user.setName(formattedName);

	    String email = updatedUser.getEmail();
	    if (email == null || email.trim().isEmpty()) {
	        throw new ValidationUtil.InvalidEmailException("Email cannot be empty");
	    }
	    user.setEmail(email.trim());

	    // Update password if provided
	    String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	    String newPassword = updatedUser.getPassword();
	    if (newPassword != null && !newPassword.isEmpty()) {
	        if (!Pattern.matches(PASSWORD_PATTERN, newPassword)) {
	            throw new ValidationUtil.InvalidPasswordException(
	                    "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
	        }
	        user.setPassword(PasswordUtility.hashPassword(newPassword));
	    }

	    // Save the updated user
	    UserEntity savedUser = userRepository.save(user);
	    
	    // Return the response
	    return modelMapper.map(savedUser, ResponseUser.class);
	}

    public Optional<ResponseAdmin> getAdminById(Long id) {
        Optional<AdminEntity> adminEntity = adminRepository.findById(id);
        return adminEntity.map(entity -> modelMapper.map(entity, ResponseAdmin.class));
    }

	 public List<ResponseUser> getAllUsers() {
	        List<UserEntity> userEntities = userRepository.findAll();
	        return userEntities.stream()
	                .map(this::convertToResponseUser) // Convert each user entity to ResponseUser
	                .collect(Collectors.toList());
	    }

	    public Optional<ResponseUser> getUserById(Long id) {
	        Optional<UserEntity> user = userRepository.findById(id);
	        return user.map(this::convertToResponseUser); // Use the conversion method
	    }

	    // Convert UserEntity to ResponseUser
	    private ResponseUser convertToResponseUser(UserEntity userEntity) {
	        ResponseUser responseUser = new ResponseUser();
	        responseUser.setId(userEntity.getId());
	        responseUser.setName(userEntity.getName());
	        responseUser.setEmail(userEntity.getEmail());
	        responseUser.setRole(userEntity.getRole());
	        responseUser.setCreatedAt(userEntity.getCreatedAt());
	        responseUser.setUpdatedAt(userEntity.getUpdatedAt());
	        responseUser.setStatus("Active"); // Default status, modify as needed
	        responseUser.setMessage("User data successfully retrieved");
	        return responseUser;
	    }

	public String deleteUserById(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return "Deleted user with ID " + id;
		} else {
			throw new ValidationUtil.UserNotFoundException("User not found");
		}
	}

	public String deleteAllUsers() {
		userRepository.deleteAll();
		return "All users are deleted";
	}

	private String formatName(String name) {
		// Split the name into parts and ensure the format 'FirstName LastName'
		String[] parts = name.split("\\s+");
		if (parts.length < 2) {
			return null; // Invalid format
		}
		// Reassemble the name with exactly one space
		return String.join(" ", parts).trim();
	}
	

	
}