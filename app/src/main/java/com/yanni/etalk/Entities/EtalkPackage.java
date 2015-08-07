package com.yanni.etalk.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbookretina on 6/07/15.
 */
public class EtalkPackage implements Parcelable {

    private String packageName;
    private String purchaseTime;
    private String validDate;
    private String totalHours;
    private String lastHours;
    private String price;
    private String status;
    private String productId;
    private String lm;
    private String packageId;


    public EtalkPackage(String packageName, String purchaseTime, String validDate, String totalHours, String lastHours, String price, String status, String productId,
                        String lm, String packageId) {

        this.packageName = packageName;
        this.purchaseTime = purchaseTime;
        this.validDate = validDate;
        this.totalHours = totalHours;
        this.lastHours = lastHours;
        this.price = price;
        this.status = status;
        this.productId = productId;
        this.lm = lm;
        this.packageId = packageId;
    }


    public EtalkPackage(Parcel in) {
        String[] data = new String[10];
        in.readStringArray(data);
        this.packageName = data[0];
        this.purchaseTime = data[1];
        this.validDate = data[2];
        this.totalHours = data[3];
        this.lastHours = data[4];
        this.price = data[5];
        this.status = data[6];
        this.productId = data[7];
        this.lm = data[8];
        this.packageId = data[9];


    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public String getLastHours() {
        return lastHours;
    }

    public void setLastHours(String lastHours) {
        this.lastHours = lastHours;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLm() {
        return lm;
    }

    public void setLm(String lm) {
        this.lm = lm;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.packageName,
                this.purchaseTime,
                this.validDate,
                this.totalHours,
                this.lastHours,
                this.price,
                this.status,
                this.productId,
                this.lm,
                this.packageId});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public EtalkPackage createFromParcel(Parcel in) {
            return new EtalkPackage(in);
        }

        public EtalkPackage[] newArray(int size) {
            return new EtalkPackage[size];
        }
    };
}
