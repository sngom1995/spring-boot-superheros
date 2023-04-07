package com.samba.springbootsuperheroes.antiHero.reposotory;

import com.samba.springbootsuperheroes.antiHero.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(
            "" +
                    "SELECT CASE WHEN COUNT(u) > 0 THEN " +
                    "TRUE ELSE FALSE END " +
                    "FROM UserEntity u " +
                    "WHERE u.email = :email")
    Boolean selectExistsByEmail(String email);

    UserEntity findByEmail(String email);
}
