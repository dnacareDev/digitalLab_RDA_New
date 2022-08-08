package com.digitalLab.Controller;

import com.digitalLab.Service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadController {
    @Autowired
    private DownloadService downloadService;
    @GetMapping("download/file")
    public ResponseEntity<Object> downloadFile(@RequestParam String path){
        return downloadService.downloadFile(path);
    }
}
