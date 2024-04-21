package com.example.myapplication;

public class UserModel extends PersonModel{
    private static UserModel instance;
        private String Sifre;
        private String Email;



    private UserModel() {}

    public static synchronized UserModel getInstance() {
        if (instance == null) {
            instance = new UserModel();
        }
        return instance;}
        public String getSifre() {
            return Sifre;
        }

        public void setSifre(String sifre) {
            Sifre = sifre;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

}

