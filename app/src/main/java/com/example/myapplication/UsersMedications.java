package com.example.myapplication;

public class UsersMedications {
    private int Id;
    private String IlacAdi;
    private String SabahKullanimi;
    private String OgleKullanimi;
    private String AksamKullanimi;

    public UsersMedications(int id, String ilacAdi, String sabahKullanimi, String ogleKullanimi, String aksamKullanimi) {
        Id = id;
        IlacAdi = ilacAdi;
        SabahKullanimi = sabahKullanimi;
        OgleKullanimi = ogleKullanimi;
        AksamKullanimi = aksamKullanimi;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIlacAdi() {
        return IlacAdi;
    }

    public void setIlacAdi(String ilacAdi) {
        this.IlacAdi = ilacAdi;
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
