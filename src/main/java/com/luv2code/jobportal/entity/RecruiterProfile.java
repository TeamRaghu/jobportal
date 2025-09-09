package com.luv2code.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {
    @Id
    private int userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;
    private String city;
    private String country;
    private String company;
    private String state;
    private String firstName;
    private String lastName;

    public RecruiterProfile(int userAccountId, Users userId, String city, String country, String company, String state, String firstName, String lastName, String profilePhoto) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.city = city;
        this.country = country;
        this.company = company;
        this.state = state;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
    }

    @Column(nullable = true,length = 64)
    private  String profilePhoto;



    public RecruiterProfile(Users users) {
    this.userId=users;
    }

    public RecruiterProfile() {

    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }
@Transient
    public String getPhotosImagePath(){
        if(profilePhoto==null) return  null;
        return "/photos/recruiter/"+userAccountId+"/"+profilePhoto;
    }
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", company='" + company + '\'' +
                ", state='" + state + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
