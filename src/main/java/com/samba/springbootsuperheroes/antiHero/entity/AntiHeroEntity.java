package com.samba.springbootsuperheroes.antiHero.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "anti_hero")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AntiHeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator ="UUID")
    @Column( updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "First name is required")
    private String firstName;
    private String lastName;
    private String house;
    private String knownAs;
    private String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(new Date());

}
