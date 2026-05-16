package ru.karpin.restaurant.menu.mapper;

import lombok.experimental.UtilityClass;
import ru.karpin.restaurant.menu.dto.MenuCreate;
import ru.karpin.restaurant.menu.dto.MenuResponse;
import ru.karpin.restaurant.menu.entity.Menu;

@UtilityClass
public class MenuMapper {

    public static MenuResponse toResponse(Menu menu) {
        return new MenuResponse(menu.getId(), menu.getName(), menu.getSeasonality(), menu.isActive());
    }

    public static Menu toMenu(MenuCreate request) {
        Menu menu = new Menu();
        menu.setName(request.name());
        menu.setSeasonality(request.seasonality());
        menu.setActive(request.isActive());
        return menu;
    }
}
