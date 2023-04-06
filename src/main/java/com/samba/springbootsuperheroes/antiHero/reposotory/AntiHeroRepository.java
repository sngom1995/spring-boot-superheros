package com.samba.springbootsuperheroes.antiHero.reposotory;


import com.samba.springbootsuperheroes.antiHero.entity.AntiHeroEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AntiHeroRepository extends CrudRepository<AntiHeroEntity, UUID> {
}
