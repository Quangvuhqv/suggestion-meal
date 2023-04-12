package com.process.repository;

import com.process.model.entity.Config;
import com.process.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
    @Query(value = "SELECT c FROM Config c JOIN Categories cg ON c.categoryId = cg.id WHERE cg.code = :code ")
    List<Config> getConfigByCategory(@Param("code")String code);
}
