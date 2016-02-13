package ug.getin.app.models;

/**
 * Created by abdussekalala on 12/31/15.
 */
public class Girl {
    public String name;
    public String date_of_birth;
    public int age;
    public String marital_status;
    public String education_level;
    public String emergency_contact;

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String contact_type;
    public String contact_number;
    public String number_of_children;

    public String getNumber_of_children() {
        return number_of_children;
    }

    public void setNumber_of_children(String number_of_children) {
        this.number_of_children = number_of_children;
    }

    public Boolean getHas_gone_for_anc() {
        return has_gone_for_anc;
    }

    public void setHas_gone_for_anc(Boolean has_gone_for_anc) {
        this.has_gone_for_anc = has_gone_for_anc;
    }

    public String getPrefered_language() {
        return prefered_language;
    }

    public void setPrefered_language(String prefered_language) {
        this.prefered_language = prefered_language;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public String getSubcounty() {
        return subcounty;
    }

    public void setSubcounty(String subcounty) {
        this.subcounty = subcounty;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Boolean has_gone_for_anc;
    public String prefered_language;
    public String village;
    public String parish;
    public String subcounty;
    public double latitude;
    public double longitude;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDate_of_birth() { return date_of_birth; }

    public void setDate_of_birth(String date_of_birth) { this.date_of_birth = date_of_birth; }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getEducation_level() {
        return education_level;
    }

    public void setEducation_level(String education_level) {
        this.education_level = education_level;
    }



}
