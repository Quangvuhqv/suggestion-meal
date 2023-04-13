package com.process.repository;

import com.process.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> getTop2ByStatusIsOrderByCreateDateDesc(Integer status);
}
