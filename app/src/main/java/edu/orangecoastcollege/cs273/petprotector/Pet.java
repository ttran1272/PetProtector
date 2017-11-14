package edu.orangecoastcollege.cs273.petprotector;

import android.net.Uri;

/**
 * Created by ttran1272 on 10/26/2017.
 */

public class Pet {

    private long mId;
    private String mName;
    private String mDetail;
    private String mPhone;
    private String petImageName;

    public Pet() {
        this(-1, "", "", "", "");
    }

    public Pet(int mId, String mName, String mDetail, String mPhone, String petImageName) {
        this.mId = mId;
        this.mName = mName;
        this.mDetail = mDetail;
        this.mPhone = mPhone;
        this.petImageName = petImageName;
    }

    public Pet(String mName, String mDetail, String mPhone) {
        this.mName = mName;
        this.mDetail = mDetail;
        this.mPhone = mPhone;
    }


    public Pet(String mName, String mDetail, String mPhone, String petImageName) {

        this.mName = mName;
        this.mDetail = mDetail;
        this.mPhone = mPhone;
        this.petImageName = petImageName;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPetImageName() {
        return petImageName;
    }

    public void setPetImageName(String petImageName) {
        this.petImageName = petImageName;
    }

    public String toString()
    {
        return "Pet{" +
                "Id=" + mId +
                ", Name='" + mName + "\'" + ", Details='" + mDetail + "\'" + ", Phone='" +
                mPhone + "\'" + ", ImageName='" + petImageName + "\'" + "}";
    }
}
