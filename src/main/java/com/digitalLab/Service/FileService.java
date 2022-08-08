package com.digitalLab.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService
{
    public String uploadAndGetFilePath(MultipartFile file, String path) throws IOException;

    public String ChangeFileName(String extension);
    
    public boolean extensionCheck(MultipartFile file);
}