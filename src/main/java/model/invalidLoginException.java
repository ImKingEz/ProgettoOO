package model;

public class invalidLoginException extends Exception{
    public invalidLoginException(boolean utenteTrovato, boolean passwordTrovata) {
        if(utenteTrovato==false){
            System.out.println("USERNAME ERRATO");
        }
        if(passwordTrovata==false){
            System.out.println("PASSWORD ERRATA");
        }
    }
}
