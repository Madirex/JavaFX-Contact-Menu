package com.madirex.javafxmenu.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona {
    private StringProperty name;
    private StringProperty gender;
    private StringProperty country;
    private StringProperty email;
    private StringProperty phone;
    private StringProperty picture;
    private IntegerProperty age;

    public Persona(){
        name = new SimpleStringProperty();
        gender = new SimpleStringProperty();
        country = new SimpleStringProperty();
        email = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        picture = new SimpleStringProperty();
        age = new SimpleIntegerProperty();
    }

    public Persona(StringProperty name, StringProperty gender, StringProperty country,
                   StringProperty email, StringProperty phone, StringProperty picture, IntegerProperty age) {
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
        this.age = age;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getPicture() {
        return picture.get();
    }

    public StringProperty pictureProperty() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture.set(picture);
    }
    public Integer getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(Integer age) {
        this.age.set(age);
    }

    @Override
    public String toString() {
        return email.get() + " | " + phone.get();
    }
}
