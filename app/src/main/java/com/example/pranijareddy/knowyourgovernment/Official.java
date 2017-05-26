package com.example.pranijareddy.knowyourgovernment;

import java.io.Serializable;

/**
 * Created by Pranijareddy on 4/5/2017.
 */

public class Official implements Serializable{

    private String locCity;



    private String locState;
    private String locZip;
    private String office;
    private String Name;
    private String address;
    private String city;
    private String State;
    private String zip;
    private String party;
    private String number ;
    private String url;
    private String email;
    private String purl;
    private String gid;
    private String fid;
    private String tid;
    private String yid;




    public Official( String office, String name, String address, String city, String state, String zip, String party, String number, String url, String email, String purl, String gid, String fid, String tid, String yid) {

        this.office = office;
        Name = name;
        this.address = address;
        this.party = party;
        this.number = number;
        this.url = url;
        this.email = email;
        this.purl = purl;
        this.gid = gid;
        this.fid = fid;
        this.tid = tid;
        this.yid = yid;
    }





    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getYid() {
        return yid;
    }

    public void setYid(String yid) {
        this.yid = yid;
    }
}
