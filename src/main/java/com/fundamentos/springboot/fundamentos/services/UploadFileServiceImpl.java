package com.fundamentos.springboot.fundamentos.services;

import com.fundamentos.springboot.fundamentos.controllers.ClienteRestController;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService{
    
    private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);
    private final static String DIRECTORY_UPLOAD = "uploads";
    @Override
    public Resource load(String namePhoto) throws MalformedURLException {
        
        Path filePath = getPath(namePhoto);
        Resource resource = null;
        
        resource = new UrlResource(filePath.toUri());
        
        if (!resource.exists() && !resource.isReadable()) {
            filePath = Paths.get("src/main/resources/static/images").resolve("not-user.png").toAbsolutePath();

            resource = new UrlResource(filePath.toUri());
            log.error("Error no se pudo cargar la imagen: "+ namePhoto);
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString().concat("_").concat(file.getOriginalFilename().replace(" ", ""));
        Path filePath = getPath(fileName);

        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

    @Override
    public boolean delete(String namePhoto) {
        if (namePhoto != null && namePhoto.length() > 0) {
            Path lastPathPhoto = Paths.get("uploads").resolve(namePhoto).toAbsolutePath();
            File lastFilePhoto = lastPathPhoto.toFile();
            if (lastFilePhoto.exists() && lastFilePhoto.canRead()){
                lastFilePhoto.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String namePhoto) {
        return Paths.get(DIRECTORY_UPLOAD).resolve(namePhoto).toAbsolutePath();
    }
    
}
