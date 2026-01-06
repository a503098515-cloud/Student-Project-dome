package com.example.backed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@NoArgsConstructor
@AllArgsConstructor*/
@Entity//Maps this class to a database table
@Table(name = "students")//Specifies the table name students in the database
public class Student {
    @Id//Marks the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Auto-increment id(1, 2, 3...)
    private Long id;
    
    @Column(name = "first_name")// @colum is maps a field to a specific column
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    private String email;
    private Double gpa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}