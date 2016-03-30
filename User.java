package com.example.second.getlocation;

import java.io.Serializable;

/**
 * Created by Anjana on 24-02-2016.
 */
class User implements Serializable {
    public long  Latitude;
    public long Longitude;
    private String UserId;
    private String LastUpdateTime;
    private String Locality;
    private String SubLocality;
    public User(){}
    public User(long latitude, long longitude, String lastUpdateTime, String locality, String subLocality, String userId){
        this.Latitude=latitude;
        this.Longitude=longitude;
        this.UserId=userId;
        this.LastUpdateTime=lastUpdateTime;
        this.Locality=locality;
        this.SubLocality=subLocality;
    }


    public long getLatitude() {
        return Latitude;
    }

    public void setLatitude(long latitude) {
        Latitude = latitude;
    }

    public long getLongitude() {
        return Longitude;
    }

    public void setLongitude(long longitude) {
        Longitude = longitude;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLastUpdateTime() {
        return LastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        LastUpdateTime = lastUpdateTime;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getSubLocality() {
        return SubLocality;
    }

    public void setSubLocality(String subLocality) {
        SubLocality = subLocality;
    }




    }








