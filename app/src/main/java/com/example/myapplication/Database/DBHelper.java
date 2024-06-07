package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.DoctorModel;
import com.example.myapplication.Model.DrugUseListModel;
import com.example.myapplication.Model.DrugUseModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.Model.UsersMedications;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
        db.execSQL("CREATE TABLE Alarms (id INTEGER PRIMARY KEY AUTOINCREMENT,medication_name TEXT,alarm_time TEXT)");
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
                String ogleKullanimi = cursor.getString(3);
                String aksamKullanimi = cursor.getString(4);


                UsersMedications usersMedications = new UsersMedications(id, ilacAdi, sabahKullanimi, ogleKullanimi, aksamKullanimi);
                kullaniciIlaclar.add(usersMedications);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return kullaniciIlaclar;
    }

    public List<UsersMedications> getUsersProgram(String tc, int id) {
        List<UsersMedications> kullaniciIlaclar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT i.IlacAdi, ip.SabahKullanimZamani, ip.OgleKullanimZamani, ip.AksamKullanimZamani \n" +
                "FROM IlacKullanimProgrami as ip \n" +
                "INNER JOIN Ilaclar as i ON ip.IlacID = i.IlacID \n" +
                "WHERE TCKimlikNo=? AND ip.IlacID=?", new String[]{tc, String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String ilacAdi = cursor.getString(0);
                String sabahKullanimi = cursor.getString(1);
                String ogleKullanimi = cursor.getString(2);
                String aksamKullanimi = cursor.getString(3);


                UsersMedications usersMedications = new UsersMedications(ilacAdi, sabahKullanimi, ogleKullanimi, aksamKullanimi);
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

    public boolean ilacKullanimiEkle(int ilacID, String tcKimlikNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
        String currentTime = sdf.format(new Date());

        ContentValues values = new ContentValues();
        values.put("IlacID", ilacID);
        values.put("TCKimlikNo", tcKimlikNo);
        values.put("KullanimZamani", currentTime);
        long result = db.insert("IlacKullanimDurumu", null, values);
        db.close();
        return result != -1;
    }

    public List<DrugUseListModel> getDrugUseList(String tc, int id) {
        List<DrugUseListModel> kullanimListesi = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase()) {
            if (db != null) {
                try (Cursor cursor = db.rawQuery("SELECT ik.KayitID, ik.IlacID, i.IlacAdi, ik.KullanimZamani " +
                                "FROM IlacKullanimDurumu as ik INNER JOIN Ilaclar as i " +
                                "ON ik.IlacID = i.IlacID WHERE TCKimlikNo=? AND ik.IlacID=?",
                        new String[]{tc, String.valueOf(id)})) {

                    if (cursor != null && cursor.moveToFirst()) { // Cursor kontrolü
                        do {
                            int programId = cursor.getInt(0);
                            int ilacId = cursor.getInt(1);
                            String ilacAdi = cursor.getString(2);
                            String kullanimZamani = cursor.getString(3);

                            DrugUseListModel drugUseListModel = new DrugUseListModel(programId, ilacId, ilacAdi, kullanimZamani);
                            kullanimListesi.add(drugUseListModel);
                        } while (cursor.moveToNext());
                    }
                }
            } else {
                Log.e("DB_ERROR", "Veritabanına erişilemiyor.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DB_ERROR", "Veritabanı işlemleri sırasında bir hata oluştu: " + e.getMessage());
        }

        return kullanimListesi;
    }

    public boolean drugList(String tc, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ik.KayitID, ik.IlacID, i.IlacAdi, ik.KullanimZamani " +
                "FROM IlacKullanimDurumu as ik INNER JOIN Ilaclar as i ON ik.IlacID = i.IlacID " +
                "WHERE ik.TCKimlikNo=? AND ik.IlacID=?", new String[]{tc, String.valueOf(id)});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public void addAlarm(String medicationName, String alarmTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("medication_name", medicationName);
        values.put("alarm_time", alarmTime);
        db.insert("Alarms", null, values);
        db.close();
    }

    public List<String> getAllAlarms() {
        List<String> alarms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Alarms", null);
        if (cursor.moveToFirst()) {
            do {
                String medicationName = cursor.getString(1);
                String alarmTime = cursor.getString(2);
                alarms.add("Medication: " + medicationName + ", Time: " + alarmTime);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alarms;
    }
}


