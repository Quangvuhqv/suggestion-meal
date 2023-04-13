package com.process.service.impl;

import com.process.model.entity.Config;
import com.process.repository.ConfigRepository;
import com.process.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigRepository repository;
    @Override
    public List<Config> getConfigByCategoryActive(String code) {
        return repository.getConfigByCategory(code, Config.Status.ACTIVE.value);
    }
}
