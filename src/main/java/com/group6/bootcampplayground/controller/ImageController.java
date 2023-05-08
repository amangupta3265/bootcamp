package com.group6.bootcampplayground.controller;

import com.group6.bootcampplayground.model.response.SetImageResponse;
import com.group6.bootcampplayground.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group6.bootcampplayground.auth.JWTService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    ImageService imageService;
    private final JWTService jwtService;

    @PostMapping("/postImage")
//    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<SetImageResponse> setImage(@RequestParam MultipartFile file, @RequestHeader("Authorization") String header) throws IOException {
        return ResponseEntity.ok((SetImageResponse) imageService.addImage(file,header));
    }

    @GetMapping("/getImage")
//    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getImage(@RequestHeader("Authorization") String header ){

        byte[] imageData= (byte[]) imageService.getImage(header);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }



}
