package com.t1;

public class appmodel {
    private String pname,timing,status;

    public appmodel(String patname,String time,String app_status)
    {
        this.pname=patname;
        this.timing=time;
        this.status=app_status;
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
}
