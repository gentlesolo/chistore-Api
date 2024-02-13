package com.chiamaka.bookstore.service;

import com.chiamaka.bookstore.model.Image;
import com.chiamaka.bookstore.model.ImageModel;
import com.chiamaka.bookstore.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageService {

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ImageRepository imageRepository;


//    @Override
    public ResponseEntity<Map> uploadImage(ImageModel imageModel) {
//        String fileName = imageModel.getFile().getOriginalFilename();
        try {
            if (imageModel.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (imageModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Image image = new Image();
//            image.setName(fileName);
            image.setName(imageModel.getName());
            image.setUrl(cloudinaryService.uploadFile(imageModel.getFile(), "folder_1"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(image);
//            String url = image.getUrl();
//            return ResponseEntity.ok(url);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}