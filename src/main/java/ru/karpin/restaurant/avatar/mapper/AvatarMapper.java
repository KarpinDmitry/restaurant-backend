package ru.karpin.restaurant.avatar.mapper;

import lombok.experimental.UtilityClass;
import ru.karpin.restaurant.avatar.dto.AvatarResponse;
import ru.karpin.restaurant.avatar.entity.Avatar;

@UtilityClass
public class AvatarMapper {
    public static AvatarResponse toResponse(Avatar avatar){
        return new AvatarResponse(avatar.getId(), avatar.getPath());
    }
}
