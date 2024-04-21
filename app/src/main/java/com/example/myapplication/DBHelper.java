package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String dbName = "IlacTakip.db";
    public static final int databaseVersion = 5;

    public DBHelper(@Nullable Context context) {
        super(context, dbName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Kullanicilar (TCKimlikNo TEXT PRIMARY KEY, Ad TEXT NOT NULL,Soyad TEXT NOT NULL, Sifre TEXT NOT NULL, Email TEXT NOT NULL)");
        db.execSQL("CREATE TABLE Doktorlar (DoktorID INTEGER PRIMARY KEY AUTOINCREMENT, TCKimlikNo TEXT UNIQUE NOT NULL, Ad TEXT NOT NULL, Soyad TEXT NOT NULL, UzmalıkAlani Text NOT NULL)");
        db.execSQL("CREATE TABLE Ilaclar (IlacID INTEGER PRIMARY KEY AUTOINCREMENT, IlacAdi TEXT UNIQUE NOT NULL)");
        db.execSQL("CREATE TABLE IlacKullanimProgrami (ProgramID INTEGER PRIMARY KEY AUTOINCREMENT, IlacID INTEGER, TCKimlikNo TEXT, SabahKullanimZamani TEXT , OgleKullanimZamani TEXT, AksamKullanimZamani TEXT , FOREIGN KEY (IlacID) REFERENCES Ilaclar(IlacID), FOREIGN KEY (TCKimlikNo) REFERENCES Kullanicilar(TCKimlikNo))");
        db.execSQL("CREATE TABLE IlacKullanimDurumu (KayitID INTEGER PRIMARY KEY AUTOINCREMENT, IlacID INTEGER, TCKimlikNo TEXT, KullanimZamani DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (IlacID) REFERENCES Ilaclar(IlacID), FOREIGN KEY (TCKimlikNo) REFERENCES Kullanicilar(TCKimlikNo))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Kullanicilar");
        db.execSQL("DROP TABLE IF EXISTS Doktorlar");
        db.execSQL("DROP TABLE IF EXISTS Ilaclar");
        db.execSQL("DROP TABLE IF EXISTS IlacKullanimProgrami");
        db.execSQL("DROP TABLE IF EXISTS IlacKullanimDurumu");
        onCreate(db);
    }

    public boolean kullaniciEkle(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TCKimlikNo", user.getTCKimlikNo());
        values.put("Ad", user.getAdi());
        values.put("Soyad", user.getSoyadi());
        values.put("Sifre", user.getSifre());
        values.put("Email", user.getEmail());

        long result = db.insert("Kullanicilar", null, values);
        db.close();

        return result != -1;
    }

    public boolean kullaniciGiris(String tc, String sifre) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Kullanicilar WHERE TCKimlikNo = ? AND Sifre = ?", new String[]{tc, sifre});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public String[] getKullaniciBilgileri(String tc) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Ad, Soyad, Sifre, Email FROM Kullanicilar WHERE TCKimlikNo = ?", new String[]{tc});

        String[] kullaniciBilgileri = new String[4];

        if (cursor.moveToFirst()) {
            int adIndex = cursor.getColumnIndex("Ad");
            int soyadIndex = cursor.getColumnIndex("Soyad");
            int sifreIndex = cursor.getColumnIndex("Sifre");
            int emailIndex = cursor.getColumnIndex("Email");

            kullaniciBilgileri[0] = cursor.getString(adIndex);
            kullaniciBilgileri[1] = cursor.getString(soyadIndex);
            kullaniciBilgileri[2] = cursor.getString(sifreIndex);
            kullaniciBilgileri[3] = cursor.getString(emailIndex);
        }

        cursor.close();
        return kullaniciBilgileri;
    }


    public boolean kullaniciTcSorgula(String Tc) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Kullanicilar WHERE TCKimlikNo = ? ", new String[]{Tc});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean doktorEkle(DoctorModel doctor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TCKimlikNo", doctor.getTCKimlikNo());
        values.put("Ad", doctor.getAdi());
        values.put("Soyad", doctor.getSoyadi());
        values.put("UzmalıkAlani", doctor.getUzmanlikAlani());

        long result = db.insert("Doktorlar", null, values);
        db.close();

        return result != -1;
    }

    public boolean doktorGiris(DoctorModel doctor) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Doktorlar WHERE Ad = ? AND TCKimlikNo = ?", new String[]{doctor.getAdi(), doctor.getTCKimlikNo()});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean doktorTcSorgula(String Tc) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Doktorlar WHERE TCKimlikNo = ? ", new String[]{Tc});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean ilacEkle(String ilacAdi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IlacAdi", ilacAdi);
        long result = db.insert("Ilaclar", null, values);
        db.close();

        return result != -1;
    }

    public List<UsersMedications> getUsersMedications(String tc) {
        List<UsersMedications> kullaniciIlaclar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select ip.IlacID,i.IlacAdi,ip.SabahKullanimZamani," +
                "ip.OgleKullanimZamani,ip.AksamKullanimZamani FROM IlacKullanimProgrami as ip" +
                " INNER JOIN Ilaclar as i on ip.IlacID = i.IlacID " +
                "WHERE TCKimlikNo=?", new String[]{tc});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String ilacAdi = cursor.getString(1);
                String sabahKullanimi = cursor.getString(2);
                String ogleKullanimi = cursor.getString(2);
                String aksamKullanimi = cursor.getString(2);


                UsersMedications usersMedications = new UsersMedications(id, ilacAdi, sabahKullanimi, ogleKullanimi, aksamKullanimi);
                kullaniciIlaclar.add(usersMedications);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return kullaniciIlaclar;
    }

    public boolean insertIlacKullanimProgrami(DrugUseModel drugUseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IlacID", drugUseModel.getId());
        values.put("TCKimlikNo", drugUseModel.getTc());
        values.put("SabahKullanimZamani", drugUseModel.getSabahKullanimi());
        values.put("OgleKullanimZamani", drugUseModel.getOgleKullanimi());
        values.put("AksamKullanimZamani", drugUseModel.getAksamKullanimi());
        long result = db.insert("IlacKullanimProgrami", null, values);

        db.close();
        return result != -1;
    }

    public void ilacKullanimiEkle(int IlacID, String TCKimlikNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IlacID", IlacID);
        values.put("TCKimlikNo", TCKimlikNo);
        db.insert("IlacKullanimDurumu", null, values);
        db.close();
    }

}
