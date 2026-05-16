package ru.karpin.restaurant.avatar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.karpin.restaurant.avatar.dto.AvatarResponse;
import ru.karpin.restaurant.avatar.service.AvatarService;

@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarResponse upload(@RequestPart("file") MultipartFile file) {
        return avatarService.upload(file);
    }

    @GetMapping("/{id}")
    public AvatarResponse get(@PathVariable Long id){
        return avatarService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        avatarService.delete(id);
    }
}
