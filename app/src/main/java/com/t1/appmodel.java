package com.t1;

public class appmodel {
    private String pname,timing,status,id,date;

    public appmodel(String patname,String date,String time,String app_status,String id)
    {
        this.pname=patname;
        this.date=date;
        this.timing=time;
        this.status=app_status;
        this.id=id;
    }
    public String getpname() {
        return pname;
    }

    public void setpname(String pname) {
        this.pname = pname;
    }

    public String gettiming() {
        return timing;
    }

    public void settiming(String timing) {
        this.timing = timing;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}
