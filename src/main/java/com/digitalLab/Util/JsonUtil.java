package com.digitalLab.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

public class JsonUtil {

	private final static String path = "upload/";
	private final static String dir = "template";
	private final static String extension = ".json";
	
	// 읽기
	public static Map<String , Object> readJson(String fileName) throws FileNotFoundException {
		
		Map<String , Object> tmeplate = new HashMap<String , Object>();
		
		JSONParser parser = new JSONParser();
		// FileReader 생성
		Reader reader = new FileReader(path+dir+"/"+fileName+extension);
		
		JSONObject jsonObject;
		
		try {
			jsonObject = (JSONObject) parser.parse(reader);
			System.out.println(jsonObject);
			JSONArray name = (JSONArray)jsonObject.get("head");
			JSONArray header = (JSONArray)jsonObject.get("body");
			
			tmeplate.put("head", name.toJSONString());
			tmeplate.put("body", header.toJSONString());
			
		}catch(ParseException e) {
			System.out.println("ParseException");
			return null;
		} catch (IOException e) {
			System.out.println("IOException");
			return null;
		}finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}
		
		return tmeplate;
	}

	public static String readJsonFile(String fileName) throws FileNotFoundException {

		JSONParser parser = new JSONParser();
		// FileReader 생성
		Reader reader = new FileReader(fileName);

		JSONObject jsonObject = null;

		JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) parser.parse(reader);
			System.out.println(jsonObject);

		}catch(ParseException e) {
			System.out.println("ParseException");
		} catch (IOException e) {
			System.out.println("IOException");
		}
			
		String result = null;
		
		try {
			result = jsonArray.toJSONString();
		}catch (NullPointerException e) {
			System.out.println("NullPointerException");
			return null;
		}finally {
			try {
				if(reader != null) {
				reader.close();
				}
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}
		
		return result;
	}
	
	// 쓰기
	public static String createJson(String code ,String head , String body) throws FileNotFoundException{
		
		Path filPath = Paths.get(path+dir+"/");
		
		String jsonText = "{\"head\":"+head+" , \"body\":"+body+"}";
		
		// 파일 생성
		try {
			try {
				Files.createDirectories(filPath);
			} catch (AccessDeniedException e) {
				System.out.println("AccessDeniedException");
			}
			Path file = Paths.get(path+dir+"/"+code+extension);
			
			Path newFilePath = Files.createFile(file);
			
		}catch(FileAlreadyExistsException e) {
			System.out.println("FileAlreadyExistsException");
			System.out.println("해당파일이 존재합니다. 업데이트를 진행합니다");
		}catch(IOException e) {
			System.out.println("IOException");
		}
		
		BufferedWriter writer = null;
		
		try {
		    writer = new BufferedWriter(new FileWriter(new File(path+dir+"/"+code+extension)));
		    writer.write(jsonText);
		    
		} catch (IOException e) {
			System.out.println("IOException");
		}finally {
			
			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}
		
		return null;
	}

	public static String createJsonFile(String fileNm, String fileDir,String jsonText){
		Path filPath = Paths.get(path + fileDir + "/");
		// 파일 생성
		Path file = null;
		try {
			try {
				Files.createDirectories(filPath);
			} catch (AccessDeniedException e) {
				System.out.println("AccessDeniedException");
			}
			file = Paths.get(path + fileDir + "/" + fileNm + extension);

			Path newFilePath = Files.createFile(file);

		}catch(FileAlreadyExistsException e) {
			System.out.println("FileAlreadyExistsException");
			System.out.println("해당파일이 존재합니다. 업데이트를 진행합니다");

		}catch(IOException e) {
			System.out.println("IOException");
			return null;
		}

		BufferedWriter writer = null;
		try {
			
			writer = new BufferedWriter(new FileWriter(file.toString()));
			writer.write(jsonText);
			
		} catch (IOException e) {
			System.out.println("IOException");
			return null;
		} catch (NullPointerException e) {
			System.out.println("NullPointerException");
			return null;
		}finally {

			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				System.out.println("IOException");
				return null;
			}
		}

		return file.toString();
	}

	public static String createStorageJson(String code, MultipartFile inputFile) throws Exception {

		Path filPath = Paths.get(path+dir+"/");

		String jsonText = fileToJsonText(inputFile);
		
		System.out.println("JsonUtil 220 : "+jsonText);
		
		if(jsonText == null){
			throw new Exception();
		}

		// 파일 생성
		try {

			try {
				Files.createDirectories(filPath);
			} catch (AccessDeniedException e) {
				System.out.println("AccessDeniedException");
			}
			Path file = Paths.get(path+dir+"/"+code+extension);

			Path newFilePath = Files.createFile(file);

		}catch(FileAlreadyExistsException e) {
			System.out.println("FileAlreadyExistsException");
			System.out.println("해당파일이 존재합니다. 업데이트를 진행합니다");

		}catch(IOException e) {
			System.out.println("IOException");
		}

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(path+dir+"/"+code+extension)));
			writer.write(jsonText);
		} catch (NullPointerException e){
			System.out.println("NullPointerException");
		} catch (IOException e) {
			System.out.println("IOException");
		}finally {
			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				System.out.println("IOException");
			}
		}

		return null;

	}

	private static String fileToJsonText(MultipartFile file) throws IOException {
		String jsonData;
		String headData =
				"\"head\":[{\"header\":\"DateTime\",\"name\":\"DateTime\"},{\"header\":\"temp1\",\"name\":\"temp1\"},{\"header\":\"humidity1\",\"name\":\"humidity1\"}," +
						"{\"header\":\"temp2\",\"name\":\"temp2\"},{\"header\":\"humidity2\",\"name\":\"humidity2\"}]";
		String bodyData = "";
		Workbook workbook = null;

		String[] file_extension = file.getOriginalFilename().split("\\.");
		String fileExtension = file_extension[1];

		if (fileExtension.equals("xlsx")) {
			workbook = new XSSFWorkbook(file.getInputStream());
		} else if (fileExtension.equals("xls")) {
			workbook = new HSSFWorkbook(file.getInputStream());
		}
			
			Sheet worksheet = null;
			
			if(workbook != null) {
				try {
					worksheet = workbook.getSheetAt(0);
				} catch (NullPointerException e) {
					System.out.println("NullPointerException");
					return null;
				}
			}else {
				throw new NullPointerException();
			}

			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int lastRowNum = worksheet.getPhysicalNumberOfRows();
			bodyData = "\"body\":[";
			
			for (int row = 4; row <= lastRowNum; row++) {
				Row rowData = worksheet.getRow(row);
				String rowStr = "";
				for (int col = 0; col <= 4; col++) {
					
					Cell cell = null;
					
					try {
						cell = rowData.getCell(col);
					} catch (NullPointerException e) {
						
						System.out.println("NullPointerException");
						if(col == 0 && row != 4){
							lastRowNum = row;
							bodyData = bodyData.substring(0, bodyData.length() - 2);
							
							System.out.println("312 break");
							
							break;
						}
					}
					
					String cellStr = "";

					if(col == 0) {
						
						Date date = null;
						
						try {
							date = cell.getDateCellValue();
						} catch (IllegalStateException e) {
							System.out.println("IllegalStateException");
							throw new IllegalStateException();
						}
						
						cellStr = transFormat.format(date);
					}else{
						cellStr = String.valueOf(cell);
					}


					if (col == 0) {
						rowStr = "{\"DateTime\" : \"" + cellStr + "\",";
					} else if (col == 1) {
						rowStr += " \"temp1\" : \"" + cellStr + "\",";
					} else if (col == 2) {
						rowStr += " \"humidity1\" : \"" + cellStr + "\",";
					} else if (col == 3) {
						rowStr += " \"temp2\" : \"" + cellStr + "\",";
					} else if (col == 4) {
						rowStr += " \"humidity2\" : \"" + cellStr + "\"}";
					}
				}
				bodyData += rowStr;
				if(row != lastRowNum){
					bodyData += ", ";
				}else{
					bodyData += "]";
				}
			}


		jsonData = "{" + headData + "," + bodyData + "}";
		
		return jsonData;
	}

	public static boolean deleteFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			if(file.delete()){
				System.out.println("성공");
				return true;
			}
			else{
				System.out.println("실패");
				return false;
			}
		}
		else{
			System.out.println("파일이 존재하지 않습니다.");
			return false;
		}

	}
}
