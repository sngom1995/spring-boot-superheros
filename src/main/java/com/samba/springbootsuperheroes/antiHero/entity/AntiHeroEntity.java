package com.samba.springbootsuperheroes.antiHero.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "anti_hero")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("anti_hero")
public class AntiHeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( updatable = false, nullable = false)
    private Integer id;

    @NotNull(message = "First name is required")
    private String firstName;
    private String lastName;
    private String house;
    private String knownAs;
    private String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(new Date());

}
