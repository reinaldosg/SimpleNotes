package com.example.hawk.mydb.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Tugas implements Parcelable{
    private int id;
    private String mataKuliah, deskripsi, tglPengumpulan;

    public Tugas() {

    }

    protected Tugas(Parcel in) {
        id = in.readInt();
        mataKuliah = in.readString();
        deskripsi = in.readString();
        tglPengumpulan = in.readString();
    }

    public static final Creator<Tugas> CREATOR = new Creator<Tugas>() {
        @Override
        public Tugas createFromParcel(Parcel in) {
            return new Tugas(in);
        }

        @Override
        public Tugas[] newArray(int size) {
            return new Tugas[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTglPengumpulan() {
        return tglPengumpulan;
    }

    public void setTglPengumpulan(String tglPengumpulan) {
        this.tglPengumpulan = tglPengumpulan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(mataKuliah);
        parcel.writeString(deskripsi);
        parcel.writeString(tglPengumpulan);
    }
}
