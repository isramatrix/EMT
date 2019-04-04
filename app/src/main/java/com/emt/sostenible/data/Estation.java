package com.emt.sostenible.data;

public class Estation {

    private String name ;
    private String lon ;
    private String city ;
    private String idx;
    private String stamp;
    private String pol;
    private String x;
    private String aqi;
    private String tz ;
    private String utime ;
    private String img ;

    public Estation(String name, String lon, String city, String idx, String stamp, String pol, String x, String aqi, String tz, String utime, String img) {
        this.name = name;
        this.lon = lon;
        this.city = city;
        this.idx = idx;
        this.stamp = stamp;
        this.pol = pol;
        this.x = x;
        this.aqi = aqi;
        this.tz = tz;
        this.utime = utime;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
