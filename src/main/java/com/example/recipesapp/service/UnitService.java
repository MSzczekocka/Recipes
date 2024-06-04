package com.example.recipesapp.service;

import com.example.recipesapp.entity.Unit;
import com.example.recipesapp.exceptions.UnitBadRequestException;
import com.example.recipesapp.exceptions.UnitNotFoundException;
import com.example.recipesapp.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public List<Unit> findAllUnits() {
        return unitRepository.findAll();
    }

    public Unit getUnitWithId(Integer id) {
        return unitRepository.findById(id).orElseThrow(UnitNotFoundException::new);
    }

    public Unit saveUnit(Unit newUnit){
        try {
            return unitRepository.save(newUnit);
        } catch (Exception exception) {
            throw new UnitBadRequestException();
        }
    }
}
