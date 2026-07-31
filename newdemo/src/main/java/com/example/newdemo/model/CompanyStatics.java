package com.example.newdemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "company_statics")
public class CompanyStatics {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "full_day", nullable = false)    
    private int fullDay;

    @Column(name = "half_day", nullable = false)
    private int halfDay;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getFullDay() {
        return fullDay;
    }

    public void setFullDay(int fullDay) {
        this.fullDay = fullDay;
    }

    public int getHalfDay() {
        return halfDay;
    }

    public void setHalfDay(int halfDay) {
        this.halfDay = halfDay;
    }
}
