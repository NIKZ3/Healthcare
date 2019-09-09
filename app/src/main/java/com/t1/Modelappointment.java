package com.t1;

public class Modelappointment {

    String name,description,id,doctoruid,doctordocument;


    public Modelappointment(String name, String description,String id,String doctoruid,String doctordocument) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.doctoruid = doctoruid;
        this.doctordocument = doctordocument;
    }


    public String getDoctordocument() { return doctordocument; }
    public void setDoctordocument(String doctordocument) { this.doctordocument = doctordocument; }

    public void setDoctoruid(String doctoruid) { this.doctoruid = doctoruid; }
    public String getDoctoruid() { return doctoruid; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
