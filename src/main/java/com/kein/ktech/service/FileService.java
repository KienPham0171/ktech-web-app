package com.kein.ktech.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileService {
    String saveMultipartFile(MultipartFile multipartFile);
}
