package com.digitalLab.Service;

import org.springframework.http.ResponseEntity;

public interface DownloadService {
    ResponseEntity<Object> downloadFile(String path);
}
