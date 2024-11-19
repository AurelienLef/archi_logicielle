package org.example.tp.s;

public class UserRegistratorService {
    private SecurityServiceTP securityServiceTP;

    {
        securityServiceTP = new SecurityServiceTP();
    }

    public void registerUser(UserTP userTP) {
        // Valider le nom d'utilisateur et le mot de passe
        if (securityServiceTP.validateUsername(userTP) && securityServiceTP.validatePassword(userTP)) {
            // Enregistrer l'utilisateur dans la base de données
            System.out.println("Utilisateur enregistré avec succès.");
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe invalide.");
        }
    }
}
