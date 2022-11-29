package com.example.photosclone.controller;

import com.example.photosclone.model.Photos;
import com.example.photosclone.service.PhotosService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@RestController
public class PhotosController {
    private final PhotosService photosService;

    public PhotosController(PhotosService photosService) {
        this.photosService = photosService;
    }

    // private List<Photos> db= List.of(new Photos("1","hello.jpg"));
    @GetMapping("/")
        public String hello () {
            return "Hello world";
        }

    @GetMapping("/photos")
       public Collection<Photos> get(){
        return photosService.get();
        }
    @GetMapping("/photos/{id}")
    public Photos get(@PathVariable String id){
       Photos photos = photosService.get(id);
       if(photos==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       return photos;
    }
    @DeleteMapping ("/photos/{id}")
    public void delete (@PathVariable String id){
        Photos photos = photosService.remove(id);
        if(photos==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }
    @PostMapping  ("/photos/")
    public Photos create(@RequestPart("data")MultipartFile file) throws IOException {

      Photos photos=  photosService.save(file.getOriginalFilename(),file.getContentType(),file.getBytes());
return photos;
    }

}
