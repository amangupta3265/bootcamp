package com.group6.bootcampplayground.service;

import com.group6.bootcampplayground.auth.JWTService;
import com.group6.bootcampplayground.model.Image;
import com.group6.bootcampplayground.model.response.GetImageResponse;
import com.group6.bootcampplayground.model.response.SetImageResponse;
import com.group6.bootcampplayground.repository.ImageRepository;
import com.group6.bootcampplayground.util.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageService {

    private final JWTService jwtService;

    @Autowired
    ImageRepository imageRepository;

    public Object addImage(MultipartFile file, String header) throws IOException {

        String token=header.substring(7);
        String email=jwtService.extractUsername(token);

        // Building complete image object by associating email and compressed data
        Image image = buildImage(file,email);

        imageRepository.save(image);

        return SetImageResponse.builder()
                .status("Profile Picture Updated!")
                .build();
    }



    private Image buildImage(MultipartFile file,String email) throws IOException {
        return  Image.builder()
                .email(email)
                .imageData(ImageUtils.compressImage(file.getBytes())).build();
    }

    public Object getImage(String header) {

        String token=header.substring(7);
        String email=jwtService.extractUsername(token);

        Image image=imageRepository.findById(email).orElseGet(()-> null);

        byte[] imageData=ImageUtils.decompressImage(image.getImageData());

        String status="fail";

        if(image!=null)
            status="Success";


        return imageData;

//        return GetImageResponse.builder()
//                .imageData(imageData)
//                .status(status).build();

    }
}
