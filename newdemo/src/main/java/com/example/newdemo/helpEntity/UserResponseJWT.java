package com.example.newdemo.helpEntity;


import com.example.newdemo.model.AttendanceEntity;
import java.time.LocalDate;
import java.util.List;

public class UserResponseJWT {

    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String status;
   // private List<AttendanceEntity> attendanceList;
    private String token; 
    // New token field

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public List<AttendanceEntity> getAttendanceList() {
//        return attendanceList;
//    }
//
//    public void setAttendanceList(List<AttendanceEntity> attendanceList) {
//        this.attendanceList = attendanceList;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
