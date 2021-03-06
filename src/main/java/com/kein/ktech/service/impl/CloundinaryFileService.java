package com.kein.ktech.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kein.ktech.service.FileService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class CloundinaryFileService implements FileService {
    private final Cloudinary cloudinary;
    @Autowired
    public CloundinaryFileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String saveMultipartFile(MultipartFile multipartFile) {
        if(multipartFile != null)
        {
            File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            try {
                convFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(multipartFile.getBytes());
                fos.close();
                JSONObject result = new JSONObject(cloudinary.uploader().upload(convFile, ObjectUtils.emptyMap()));
                //Map uploadResult = cloudinary.uploader().upload(convFile, ObjectUtils.emptyMap());
                String url = (String) result.get("url");
                return url;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
