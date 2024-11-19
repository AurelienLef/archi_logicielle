package org.example.tp.s;

public class UserCoService {
    private SecurityServiceTP securityServiceTP;

    {
        securityServiceTP = new SecurityServiceTP();
    }

    public void loginUser(UserTP user) {
        // Valider le nom d'utilisateur et le mot de passe
        if (securityServiceTP.validateUsername(user) && securityServiceTP.validatePassword(user)) {
            // Authentifier l'utilisateur
            System.out.println("Utilisateur connecté avec succès.");
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe invalide.");
        }
    }
}
