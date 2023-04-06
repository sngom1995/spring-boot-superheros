package com.samba.springbootsuperheroes.antiHero.controller;

import com.samba.springbootsuperheroes.antiHero.dto.AntiHeroDto;
import com.samba.springbootsuperheroes.antiHero.entity.AntiHeroEntity;
import com.samba.springbootsuperheroes.antiHero.service.AntiHeroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/anti-heroes")
public class AntiHeroController {
    private final AntiHeroService antiHeroService;
    private final ModelMapper mapper;

    private AntiHeroDto convertToDto(AntiHeroEntity entity) {
        return mapper.map(entity, AntiHeroDto.class);
    }
    private AntiHeroEntity convertToEntity(AntiHeroDto dto) {
        return mapper.map(dto, AntiHeroEntity.class);
    }

    @GetMapping("/{id}")
    public AntiHeroDto getAntiHeroById(@PathVariable("id") UUID id) {
        return convertToDto(antiHeroService.findAntiHeroByIdOrThrow(id));
    }

    @PostMapping
    public AntiHeroDto createAntiHero(@RequestBody AntiHeroDto dto) {
       var entity = convertToEntity(dto);
       var savedEntity = antiHeroService.save(entity);
       return convertToDto(savedEntity);
    }

    @PutMapping("/{id}")
    public void updateAntiHero(@Valid @PathVariable("id") UUID id, @RequestBody AntiHeroDto dto) {
       if (!id.equals(dto.getId())){

           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in path and body are not the same");
       }
         var entity = convertToEntity(dto);
       antiHeroService.updateAntiHero(id, entity);
    }

    @DeleteMapping("/{id}")
    public void deleteAntiHero(@PathVariable("id") UUID id) {
        antiHeroService.deleteById(id);
    }
}
