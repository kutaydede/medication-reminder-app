package com.example.myapplication;

public class DrugUseModel {
    private int Id;
    private String Tc;
    private String SabahKullanimi;
    private String OgleKullanimi;
    private String AksamKullanimi;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTc() {
        return Tc;
    }

    public void setTc(String tc) {
        Tc = tc;
    }

    public String getSabahKullanimi() {
        return SabahKullanimi;
    }

    public void setSabahKullanimi(String sabahKullanimi) {
        SabahKullanimi = sabahKullanimi;
    }

    public String getOgleKullanimi() {
        return OgleKullanimi;
    }

    public void setOgleKullanimi(String ogleKullanimi) {
        OgleKullanimi = ogleKullanimi;
    }

    public String getAksamKullanimi() {
        return AksamKullanimi;
    }

    public void setAksamKullanimi(String aksamKullanimi) {
        AksamKullanimi = aksamKullanimi;
    }
}
