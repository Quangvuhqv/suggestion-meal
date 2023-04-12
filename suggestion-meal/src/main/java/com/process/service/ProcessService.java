package com.process.service;

import com.process.model.dto.MenuDTO;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ProcessService {
    void generateMenu() throws NoSuchAlgorithmException;

    List<MenuDTO> getAllMenu();
}
