package org.example.tp.s;

public class SecurityServiceTP {
    public boolean validateUsername(UserTP userTP) {
        // Valider le nom d'utilisateur (ex: longueur, caractères autorisés)
        return userTP.getUsername().length() >= 5 && userTP.getPassword().matches("[a-zA-Z_0-9]+");
    }

    public boolean validatePassword(UserTP userTP) {
        // Valider le mot de passe (ex: longueur, complexité)
        return userTP.getPassword().length() >= 8 && userTP.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    }
}
