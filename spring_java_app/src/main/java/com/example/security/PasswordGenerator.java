package com.example.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "1234";
        
        System.out.println("Generando hashes para contraseña: " + password);
        System.out.println("========================================");
        
        // Generar hash
        String hash = encoder.encode(password);
        System.out.println("Hash generado: " + hash);
        
        // Verificar que funciona
        boolean matches = encoder.matches(password, hash);
        System.out.println("¿El hash coincide con '1234'? " + matches);
        
        System.out.println("\n========================================");
        System.out.println("INSTRUCCIONES:");
        System.out.println("1. Copia el hash de arriba");
        System.out.println("2. Usalo en el SQL para insertar usuarios");
        System.out.println("========================================");
    }
}
