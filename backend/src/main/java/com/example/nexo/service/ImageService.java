package com.example.nexo.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService {
    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try {
             String publicId = UUID.randomUUID().toString();

            Map params = ObjectUtils.asMap(
                    "public_id", publicId,
                    "folder", "nexo-products");
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            return uploadResult.get("secure_url").toString();
        } catch(IOException e) {
            throw new RuntimeException("Error when uploading image", e);
        }
    }
}