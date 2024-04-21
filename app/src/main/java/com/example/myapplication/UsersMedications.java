package com.example.myapplication;

public class UsersMedications {
    private int id;
    private String ilacAdi;
    private String sabahKullanimi;
    private String ogleKullanimi;
    private String aksamKullanimi;

    public UsersMedications(int id, String ilacAdi, String sabahKullanimi, String ogleKullanimi, String aksamKullanimi) {
        this.id = id;
        this.ilacAdi = ilacAdi;
        this.sabahKullanimi = sabahKullanimi;
        this.ogleKullanimi = ogleKullanimi;
        this.aksamKullanimi = aksamKullanimi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIlacAdi() {
        return ilacAdi;
    }

    public void setIlacAdi(String ilacAdi) {
        this.ilacAdi = ilacAdi;
    }

    public String getSabahKullanimi() {
        return sabahKullanimi;
    }

    public void setSabahKullanimi(String sabahKullanimi) {
        this.sabahKullanimi = sabahKullanimi;
    }

    public String getOgleKullanimi() {
        return ogleKullanimi;
    }

    public void setOgleKullanimi(String ogleKullanimi) {
        this.ogleKullanimi = ogleKullanimi;
    }

    public String getAksamKullanimi() {
        return aksamKullanimi;
    }

    public void setAksamKullanimi(String aksamKullanimi) {
        this.aksamKullanimi = aksamKullanimi;
    }
}
