package com.process.service.impl;

import com.process.model.entity.Menu;
import com.process.repository.MenuRepository;
import com.process.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository repository;

    public List<Menu> getLastTop2MenuActive() {
        return repository.getTop2ByStatusIsOrderByCreateDateDesc(Menu.Status.ACTIVE.value);
    }

    @Override
    public void saveAll(List<Menu> menus) {
        repository.saveAll(menus);
    }

    @Override
    public List<Menu> findAll() {
        return repository.findAll();
    }
}
