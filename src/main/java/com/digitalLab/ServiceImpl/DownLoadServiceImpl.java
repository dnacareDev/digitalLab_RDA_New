package com.digitalLab.ServiceImpl;

import com.digitalLab.Service.DownloadService;
import com.digitalLab.Util.Base64Encoding;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DownLoadServiceImpl implements DownloadService {

    @Override
    public ResponseEntity<Object> downloadFile(String path) {
        try
        {	
        	String[] fileArr = path.split("\\.");
        	
        	String extention = null;
        	
        	try {
        		extention = Base64Encoding.base64Decoding(fileArr[1]);
			} catch (UnsupportedEncodingException e) {
				System.out.println("UnsupportedEncodingException");
			}
        	
        	path = fileArr[0]+"."+extention;
        	
        	System.out.println(path);
        	
            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
            
            File file = new File(path);
            
        	System.out.println("file : "+file);
            
            // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());

            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
        }
        catch(IOException e)
        {
            System.out.println("IOException");

            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }
    }
}
