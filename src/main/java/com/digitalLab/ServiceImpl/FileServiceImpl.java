package com.digitalLab.ServiceImpl;

import com.digitalLab.Service.FileService;
import com.digitalLab.Util.Base64Encoding;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService
{
    private static final String serverPath = "upload/";

    public String ChangeFileName(String extension)
    {
        String fileName = "";

        Date date = new Date();

        fileName = date.getTime() +"." + extension;

        return fileName;
    }

    @Override
    public String uploadAndGetFilePath(MultipartFile file, String path) throws IOException
    {
        Date date = new Date();
        String date_name = (1900 + date.getYear()) + "" + (date.getMonth() + 1) + "" + date.getDate() + "" + date.getHours() + "" + date.getMinutes() + "" + date.getSeconds();

        String[] file_extension = file.getOriginalFilename().split("\\.");
        
        String original_name = date_name+"."+file_extension[file_extension.length-1];
        String file_name = date_name + "." + file_extension[file_extension.length-1];

        path = serverPath + path;

        String uploadDirectoryPath = "upload";
        Path uploadDirectoryLocation = Paths.get(uploadDirectoryPath).toAbsolutePath().normalize();
        
        System.out.println("uploadDirectoryLocation aaaaa : " + uploadDirectoryLocation);
        
        File uploadPath = new File(uploadDirectoryLocation.toString());
        
        if(!uploadPath.exists())
        {
        	if(uploadPath.mkdir()) {
        		System.out.println("mkdir success");
        	}else{
        		System.out.println("mkdir fail");
        	};
        }
        
        Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
        File filePath = new File(fileLocation.toString());
        
        if(!filePath.exists()){
        	if(filePath.mkdir()){
        		System.out.println("mkdir success");
        	}else{
        		System.out.println("mkdir fail");
        	};
        }

        Path targetLocation = fileLocation.resolve(original_name);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        // 확장자 64 인코딩
        String extention = Base64Encoding.base64Encoding(file_extension[file_extension.length-1]);
        file_name = date_name+"."+extention;
        
        return  path + "/" + file_name;
    }
    
    // 확장자 체크
	@Override
	public boolean extensionCheck(MultipartFile file) {
		// TODO Auto-generated method stub
		
		String fileName = file.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if(ext.equals("xlsx") || ext.equals("xls") || ext.equals("hwp") || ext.equals("pdf")) {
			
			return false;
		}
		
		return true;
	}
}