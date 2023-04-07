package com.samba.springbootsuperheroes.antiHero.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( updatable = false, nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;
    private String mobileNumber;
    private Byte[] storedHash;
    private Byte[] storedSalt;

    public UserEntity(String email, String mobileNumber) {
        this.email = email;
        this.mobileNumber = mobileNumber;
    }
}
