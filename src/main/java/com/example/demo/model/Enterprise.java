package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "enterprise")
@NoArgsConstructor  // Constructeur par défaut explicite
@AllArgsConstructor // Constructeur avec tous les paramètres

public class Enterprise {
    @Id
    @Column(name = "enterprise_id") // Ensures the correct column name in the database
    private String enterpriseId; // Renamed to follow Java naming conventions

    private int numOfAgencies; // Changed to camelCase
    
    // Constructeur pour désérialisation depuis String
    public Enterprise(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    // No need for explicit getters and setters due to @Data annotation
}
