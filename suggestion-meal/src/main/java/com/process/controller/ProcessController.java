package com.process.controller;

import com.process.model.dto.MenuDTO;
import com.process.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping(value="/meal")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @PostMapping(value="/generate")
    public void generateMeal() throws NoSuchAlgorithmException {
        processService.generateMenu();
    }

    @GetMapping(value="/all")
    public ResponseEntity<List<MenuDTO>> getAllMeal() {
        return ResponseEntity.ok().body(processService.getAllMenu());
    }
}
