package com.samba.springbootsuperheroes.antiHero.service;


import com.samba.springbootsuperheroes.antiHero.entity.AntiHeroEntity;
import com.samba.springbootsuperheroes.antiHero.reposotory.AntiHeroRepository;
import com.samba.springbootsuperheroes.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AntiHeroService {
    private final AntiHeroRepository antiHeroRepository;

    public Iterable<AntiHeroEntity> findAll() {
        return antiHeroRepository.findAll();
    }

    public AntiHeroEntity save(AntiHeroEntity antiHeroEntity) {
        return antiHeroRepository.save(antiHeroEntity);
    }

    public AntiHeroEntity findAntiHeroByIdOrThrow(Integer id) {
        return findOrThrow(id);
    }

    private AntiHeroEntity findOrThrow(Integer id) {
        return antiHeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anti Hero by id " + id + " was not found"));
    }
    public void deleteById(Integer id) {
        antiHeroRepository.deleteById(id);
    }
    public void removeAntHero(AntiHeroEntity antiHeroEntity) {
        antiHeroRepository.delete(antiHeroEntity);
    }

    public void updateAntiHero(Integer id,AntiHeroEntity antiHeroEntity) {
        findOrThrow(id);
        antiHeroRepository.save(antiHeroEntity);
    }
}
