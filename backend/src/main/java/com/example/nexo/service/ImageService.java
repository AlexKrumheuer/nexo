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
        if (file == null || file.isEmpty()) {
            System.err.println("❌ [ImageService] Erro: Arquivo recebido é nulo ou vazio.");
            throw new RuntimeException("Arquivo inválido.");
        }

        if (cloudinary == null) {
            System.err.println("❌ [ImageService] Erro Fatal: O Bean do Cloudinary é NULL. Verifique o CloudinaryConfig.");
            throw new RuntimeException("Erro de configuração do Cloudinary.");
        }
        try {
                System.out.println("➡️ [ImageService] Iniciando upload...");
            System.out.println("   📄 Arquivo: " + file.getOriginalFilename());
            System.out.println("   📏 Tamanho: " + file.getSize() + " bytes");
            String publicId = UUID.randomUUID().toString();

            Map params = ObjectUtils.asMap(
                    "public_id", publicId,
                    "folder", "nexo-products");

            System.out.println("   🚀 Enviando para API do Cloudinary...");
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            System.out.println("   ✅ Retorno do Cloudinary recebido!");

            Object url = uploadResult.get("secure_url");
            if (url == null) {
                System.err.println("   ⚠️ Aviso: O Cloudinary não retornou 'secure_url'. Retorno completo: " + uploadResult);
                throw new RuntimeException("Upload falhou, URL vazia.");
            }    

            return uploadResult.get("secure_url").toString();
        } catch(IOException e) {
            throw new RuntimeException("Error when uploading image", e);
        }
    }
}