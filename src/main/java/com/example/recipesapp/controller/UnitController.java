package com.example.recipesapp.controller;

import com.example.recipesapp.entity.Unit;
import com.example.recipesapp.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/unit")
public class UnitController {
    private final UnitService unitService;

    @GetMapping("/all")
    public ResponseEntity<List<Unit>> getAllUnits() {
        List<Unit> allUnits = unitService.findAllUnits();
        return new ResponseEntity<>(allUnits, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Unit> addUnit(@RequestBody Unit unit) {
        Unit newUnit = unitService.saveUnit(unit);
        return new ResponseEntity<>(newUnit, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Unit> getUnit(@PathVariable final Integer id) {
        Unit unit = unitService.getUnitWithId(id);
        return new ResponseEntity<>(unit, HttpStatus.OK);
    }
}
