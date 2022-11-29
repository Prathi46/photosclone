package com.example.photosclone.controller;

import com.example.photosclone.model.Photos;
import com.example.photosclone.service.PhotosService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DownloadController {
    private final PhotosService photosService;

    public DownloadController(PhotosService photosService) {
        this.photosService = photosService;
    }
@GetMapping("download/{id}")
    public ResponseEntity<byte[]>download(@PathVariable String id){
        Photos photos=photosService.get(id);
        if (photos==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        byte[]data=photos.getData();
        HttpHeaders headers=new HttpHeaders();
          headers.setContentType(MediaType.valueOf(photos.getContentType()));
        ContentDisposition build= ContentDisposition
                .builder("attachment")
                .filename(photos.getFilename())
                        .build();
          headers.setContentDisposition(build);
        return new ResponseEntity<>(data,headers, HttpStatus.OK);
    }
}
