package com.t1;

public class appmodel {
    private String pname,timing,status,id,date;

    public appmodel(String patname,String time,String app_status,String date,String id)
    {
        this.pname=patname;
        this.timing=time;
        this.status=app_status;
        this.id=id;
        this.date=date;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
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

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}
