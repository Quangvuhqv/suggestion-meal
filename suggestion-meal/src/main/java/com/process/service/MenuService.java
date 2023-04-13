package com.process.service;

import com.process.model.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getLastTop2MenuActive();

    void saveAll(List<Menu> menus);

    List<Menu> findAll();

}
