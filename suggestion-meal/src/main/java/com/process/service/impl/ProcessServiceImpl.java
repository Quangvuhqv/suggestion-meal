package com.process.service.impl;

import com.process.exception.WrongDataException;
import com.process.model.dto.MenuDTO;
import com.process.model.entity.Config;
import com.process.model.entity.Ingredient;
import com.process.model.entity.Menu;
import com.process.repository.IngredientRepository;
import com.process.service.ConfigService;
import com.process.service.MenuService;
import com.process.service.ProcessService;
import com.process.util.FunctionCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ConfigService configService;
    @Autowired
    private MenuService menuService;

    public void generateMenu() throws NoSuchAlgorithmException {
        List<Ingredient> ingredients = ingredientRepository.getAllByStatusIsOrderByIngredientType(Ingredient.Status.ACTIVE.value);
        if (CollectionUtils.isEmpty(ingredients)) {
            return;
        }
        List<Config> mealTypes = configService.getConfigByCategoryActive(Config.CategoryType.MEAL_TYPE.name());
        if (CollectionUtils.isEmpty(mealTypes)) {
            return;
        }
        Map<Long, List<Ingredient>> map = ingredients.stream().collect(Collectors.groupingBy(Ingredient::getIngredientType));
        List<Menu> last2Menu = menuService.getLastTop2MenuActive();
        List<Menu> menus = new ArrayList<>();
        int indexOfMealType = this.getIndexOfCurrentMealType(mealTypes);
        Random random = SecureRandom.getInstanceStrong();
        LocalDate effectDate = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            Menu menu = new Menu();
            int finalIndexOfMealType = indexOfMealType;
            LocalDate finalEffectDate = effectDate;
            map.forEach((type, list) -> {
                menu.setMealType(mealTypes.get(finalIndexOfMealType).getId());
                menu.setEffectDate(finalEffectDate);
                menu.setStatus(Menu.Status.ACTIVE.value);
                menu.addIngredient(this.getSuggestIngredient(random, list, last2Menu, type));
            });
            if (indexOfMealType >= mealTypes.size() - 1) {
                indexOfMealType = 0;
                effectDate = effectDate.plusDays(1);
            } else {
                indexOfMealType += 1;
            }
            if (!CollectionUtils.isEmpty(menu.getIngredients())) {
                menus.add(menu);
                last2Menu.add(menu);
                last2Menu.remove(0);
            }
        }
        if (!CollectionUtils.isEmpty(menus)) {
            log.info("menu: {}", menus);
            menuService.saveAll(menus);
        }

    }

    private Ingredient getSuggestIngredient(Random random, final List<Ingredient> list, List<Menu> last2Menu, Long type) {
        if (last2Menu != null && last2Menu.size() >= 2) {
            List<Ingredient> tempIngredient = new ArrayList<>(list);
            Set<Ingredient> ingredientsInLast3Menu = last2Menu
                    .stream()
                    .flatMap(menu -> menu.getIngredients().stream())
                    .collect(Collectors.toSet());
            ingredientsInLast3Menu = ingredientsInLast3Menu
                    .stream()
                    .filter(ingredient -> type.equals(ingredient.getIngredientType()))
                    .collect(Collectors.toSet());
            tempIngredient.removeAll(ingredientsInLast3Menu);
            if (!CollectionUtils.isEmpty(tempIngredient)) {
                return tempIngredient.get(this.getRandomIndex(tempIngredient.size() - 1, random));
            }
        }
        return list.get(this.getRandomIndex(list.size() - 1, random));
    }

    private int getRandomIndex(int size, Random random) {
        return size > 0 ? random.nextInt(size) : 0;
    }

    private int getIndexOfCurrentMealType(List<Config> mealTypes) {
        Config currentMeal = mealTypes.stream().filter(config ->
                        !LocalTime.now()
                                .isAfter(FunctionCommon.convertStringToLocalTime(config.getValue())))
                .findFirst()
                .orElse(null);
        return currentMeal == null ? 0 : currentMeal.getOrderBy();
    }

    @Override
    public List<MenuDTO> getAllMenu() {
        List<Menu> menus = menuService.findAll();
        List<Config> mealTypes = configService.getConfigByCategoryActive(Config.CategoryType.MEAL_TYPE.name());
        if (CollectionUtils.isEmpty(mealTypes)) {
            throw new WrongDataException("config");
        }
        List<MenuDTO> results = new ArrayList<>();
        menus.forEach(menu -> {
            MenuDTO result = new MenuDTO();
            BeanUtils.copyProperties(menu, result);
            result.setMealName(mealTypes.get(Math.toIntExact(menu.getMealType()) - 1).getName());
            result.setMenu(this.getMenu(menu.getIngredients()));
            results.add(result);
        });
        return results;
    }

    private String getMenu(Set<Ingredient> ingredients) {
        return ingredients.stream().sorted(Comparator.comparing(Ingredient::getIngredientType)).map(Ingredient::getName).collect(Collectors.joining(","));
    }
}
