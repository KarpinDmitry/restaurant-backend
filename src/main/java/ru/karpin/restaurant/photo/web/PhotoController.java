package ru.karpin.restaurant.photo.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.karpin.restaurant.photo.dto.PhotoResponse;
import ru.karpin.restaurant.photo.service.PhotoService;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoResponse upload(@RequestPart("file") MultipartFile file) {
        return service.upload(file);
    }

    @GetMapping("/{id}")
    public PhotoResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
