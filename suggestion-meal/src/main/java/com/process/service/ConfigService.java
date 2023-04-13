package com.process.service;

import com.process.model.entity.Config;

import java.util.List;

public interface ConfigService {
    List<Config> getConfigByCategoryActive(String code);
}
