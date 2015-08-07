package com.yanni.etalk.Entities;

/**
 * Created by macbookretina on 13/07/15.
 */
public class EtalkClass {
    private String packageId;
    private String lm;
    private String period;
    private String time;
    private String status;


    public EtalkClass(String packageId, String lm, String period, String time, String status) {
        this.packageId = packageId;
        this.lm = lm;
        this.period = period;
        this.time = time;
        this.status = status;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getLm() {
        return lm;
    }

    public void setLm(String lm) {
        this.lm = lm;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

