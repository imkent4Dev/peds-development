package com.lyhorng.pedssystem.model.agency;

import com.lyhorng.pedssystem.model.property.building.BuildingSourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name; // CPL, LUCKY, KEY, VTRUST, KNIGHT_FRANK
    
    @Column(nullable = false)
    private String displayName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_type_id", nullable = false)
    private BuildingSourceType sourceType;
}
