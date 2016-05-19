package controllers;

import models.Farmer;
import org.mindrot.jbcrypt.BCrypt;
import play.mvc.Controller;

public class Session extends Controller {

    public static class Login {

        public String useremail;
        public String password;

        public String validate() {
            if(Farmer.authenticate(useremail, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    public static String encryptPassword(String cleanPassword) {
        if (cleanPassword == null) return null;
        return BCrypt.hashpw(cleanPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String candidate, String encrypted) {
        if (candidate == null || encrypted == null) return false;
        return BCrypt.checkpw(candidate, encrypted);
    }




    public static boolean isSessionOwner(Farmer farmer) {
        if (farmer.useremail.equals(session("connected"))) return true;
        else return false;
    }

}
