package com.example.myapplication;

public class DrugUseListModel {
    private int programId;
    private int ilacId;

    private String ilacAdi;
    private String kullanimZamani;

    public DrugUseListModel(int programId, int ilacId, String ilacAdi, String kullanimZamani) {
        this.programId = programId;
        this.ilacId = ilacId;
        this.ilacAdi = ilacAdi;
        this.kullanimZamani = kullanimZamani;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getIlacId() {
        return ilacId;
    }

    public void setIlacId(int ilacId) {
        this.ilacId = ilacId;
    }

    public String getIlacAdi() {
        return ilacAdi;
    }

    public void setIlacAdi(String ilacAdi) {
        this.ilacAdi = ilacAdi;
    }

    public String getKullanimZamani() {
        return kullanimZamani;
    }

    public void setKullanimZamani(String kullanimZamani) {
        this.kullanimZamani = kullanimZamani;
    }
}
