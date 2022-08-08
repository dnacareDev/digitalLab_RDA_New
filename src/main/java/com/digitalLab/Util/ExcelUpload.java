package com.digitalLab.Util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.digitalLab.Entity.Equipment;
import com.digitalLab.Entity.Reagent;
import com.digitalLab.Entity.Seed;

@Service
public class ExcelUpload {

	public Map<String, Object> excel_list(MultipartFile excelFile) {

		Map<String, Object> map = new HashMap<String, Object>();

		String file_name = excelFile.getOriginalFilename();

		if (file_name.contains(".xlsx")) {
			map = upload_xlsx(excelFile);
		} else {
			map = upload_xls(excelFile);
		}

		return map;
	}

	// xlsx 업로드
	private Map<String, Object> upload_xlsx(MultipartFile excelFile) {
		String error = "";

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> headList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>>();

		try {

			OPCPackage opcPackage;
			opcPackage = OPCPackage.open(excelFile.getInputStream());

			XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = sheet.getRow(i);

				if (isRowEmpty(row)) {
					continue;
				}

				if (i == 0) {
					for (int j = 0; j < row.getLastCellNum(); j++) {

						Map<String, Object> header = new HashMap<String, Object>();

						XSSFCell cell = row.getCell(j);
						header.put("header", cellReader(cell));
						header.put("name", cellReader(cell));
						headList.add(header);
					}

				} else {

					Map<String, Object> rowMap = new HashMap<String, Object>();
					for (int j = 0; j < headList.size(); j++) {

						XSSFCell cell = row.getCell(j);
						rowMap.put((String) headList.get(j).get("name"), cellReader(cell));
					}
					bodyList.add(rowMap);
				}
			}

			map.put("head", headList);
			map.put("body", bodyList);

		} catch (IllegalArgumentException e) {
			
			System.out.println("IllegalArgumentException");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (IOException ex) {
			
			System.out.println("IOException");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (InvalidFormatException e) {
			System.err.println("InvalidFormatException");
		}

		return map;
	}

	// xls 업로드
	private Map<String, Object> upload_xls(MultipartFile excelFile) {
		String error = "";
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> headList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>>();
		
		System.out.println(excelFile.getOriginalFilename());
		
		try {

			HSSFWorkbook workBook = new HSSFWorkbook(excelFile.getInputStream());
			HSSFSheet sheet = workBook.getSheetAt(0);

			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {

				HSSFRow row = sheet.getRow(i);

				if (isRowEmpty(row)) {
					continue;
				}

				if (i == 0) {
					for (int j = 0; j < row.getLastCellNum(); j++) {

						Map<String, Object> header = new HashMap<String, Object>();

						HSSFCell cell = row.getCell(j);
						header.put("header", cellReader(cell));
						header.put("name", cellReader(cell));
						headList.add(header);
					}

				} else {

					Map<String, Object> rowMap = new HashMap<String, Object>();
					for (int j = 0; j < headList.size(); j++) {

						HSSFCell cell = row.getCell(j);

						rowMap.put((String) headList.get(j).get("name"), cellReader(cell));
					}
					bodyList.add(rowMap);
				}
			}

			map.put("head", headList);
			map.put("body", bodyList);
		
		} 
		catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		} catch (IOException ex) {
			System.out.println("IOException");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
		
		return map;
	}

	// 엑셀 타입처리
	private String cellReader(XSSFCell cell) {
		String value = "";
		if (cell != null) {
			CellType ct = cell.getCellType();
			switch (ct) {

			case FORMULA:
				value = cell.getCellFormula();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					value = Integer.toString((int) Double.parseDouble(cell.getNumericCellValue() + ""));
				}
				break;
			case STRING:
				value = cell.getStringCellValue() + "";
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue() + "";
				break;
			case ERROR:
				value = cell.getErrorCellValue() + "";
				break;
			}
		}

		return value;
	}

	private String cellReader(HSSFCell cell) {
		String value = "";
		if (cell != null) {
			CellType ct = cell.getCellType();
			switch (ct) {

			case FORMULA:
				value = cell.getCellFormula();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					value = Integer.toString((int) Double.parseDouble(cell.getNumericCellValue() + ""));
				}
				break;
			case STRING:
				value = cell.getStringCellValue() + "";
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue() + "";
				break;
			case ERROR:
				value = cell.getErrorCellValue() + "";
				break;
			}
		}

		return value;
	}

	public static boolean isRowEmpty(Row row) {
		boolean isEmpty = true;
		DataFormatter dataFormatter = new DataFormatter();
		if (row != null) {
			for (Cell cell : row) {
				if (dataFormatter.formatCellValue(cell).trim().length() > 0) {
					isEmpty = false;
					break;
				}
			}
		}
		return isEmpty;
	}

	// 시료 바인딩
	public Seed bindSeed(List<String> cellList) {

		Seed seed = new Seed();

		seed.setSeed_sender(cellList.get(0));
		seed.setSend_date(cellList.get(1));
		seed.setSeed_receiver(cellList.get(2));
		seed.setReceive_date(cellList.get(3));
		seed.setGenetic_id(Integer.parseInt(cellList.get(4)));
		seed.setSeed_amount(Integer.parseInt(cellList.get(5)));
		seed.setEach_id(Integer.parseInt(cellList.get(6)));
		seed.setSeed_type(Integer.parseInt(cellList.get(7)));
		seed.setPlace_id(Integer.parseInt(cellList.get(8)));
		seed.setSeed_temp(cellList.get(9));
		seed.setSeed_manager(cellList.get(10));
		seed.setSeed_comment(cellList.get(11));

		return seed;
	}

	// 시약 바인딩
	public Reagent bindReagent(List<String> cellList) {

		Reagent reagent = new Reagent();

		reagent.setReagent_name(cellList.get(0));
		reagent.setReagent_cas(cellList.get(1));
		reagent.setReagent_density(cellList.get(2));
		reagent.setReagent_standard(cellList.get(3));
		reagent.setReagent_formula(cellList.get(4));
		reagent.setReagent_nick_e(cellList.get(5));
		reagent.setReagent_nick_k(cellList.get(6));
		reagent.setReagent_production(cellList.get(7));
		reagent.setReagent_amount(Integer.parseInt(cellList.get(8)));
		reagent.setEach_id(Integer.parseInt(cellList.get(9)));
		reagent.setPlace_id(Integer.parseInt(cellList.get(10)));
		reagent.setReagent_comment(cellList.get(11));

		return reagent;
	}

	// 장비 바인딩
	public Equipment bindEquipment(List<String> cellList) {

		Equipment equipment = new Equipment();

		equipment.setEquipment_name(cellList.get(0));
		equipment.setEquipment_code(cellList.get(1));
		equipment.setEquipment_date(cellList.get(2));
		equipment.setEquipment_price(Integer.parseInt(cellList.get(3)));
		equipment.setEquipment_range(cellList.get(4));
		equipment.setEquipment_public(cellList.get(5));
		equipment.setEquipment_purpose(cellList.get(6));
		equipment.setEquipment_place(cellList.get(7));
		equipment.setEquipment_regist(cellList.get(8));
		equipment.setEquipment_manage(cellList.get(9));
		equipment.setEquipment_method(Integer.parseInt(cellList.get(10)));

		return equipment;
	}

	/*
	 * public List<Object> excel_list(MultipartFile excelFile, String type) {
	 * 
	 * List<Object> list = new ArrayList<Object>();
	 * 
	 * System.out.println("excel type : "+type);
	 * 
	 * try {
	 * 
	 * String file_name = excelFile.getOriginalFilename();
	 * 
	 * if (file_name.contains(".xlsx")) { list = upload_xlsx(excelFile, type); }
	 * else { list = upload_xls(excelFile, type); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return list; }
	 * 
	 * // xlsx 업로드 private List<Object> upload_xlsx(MultipartFile excelFile, String
	 * type) { String error = "";
	 * 
	 * List<Object> list = new ArrayList<Object>();
	 * 
	 * try {
	 * 
	 * OPCPackage opcPackage; opcPackage =
	 * OPCPackage.open(excelFile.getInputStream());
	 * 
	 * XSSFWorkbook workbook = new XSSFWorkbook(opcPackage); XSSFSheet sheet =
	 * workbook.getSheetAt(0);
	 * 
	 * for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	 * 
	 * XSSFRow row = sheet.getRow(i);
	 * 
	 * if (null == row) { continue; }
	 * 
	 * List<String> cellList = new ArrayList<String>(); for (int j = 0; j <
	 * row.getLastCellNum(); j++) { XSSFCell cell = row.getCell(j);
	 * cellList.add(cellReader(cell)); }
	 * 
	 * try {
	 * 
	 * if (type.equals("seed")) { Seed data = bindSeed(cellList); list.add(data); }
	 * else if(type.equals("reagent")) { Reagent data = bindReagent(cellList);
	 * list.add(data); } else if(type.equals("equipment")){ Equipment data =
	 * bindEquipment(cellList); list.add(data); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * } catch (IllegalArgumentException e) { error = e.getMessage();
	 * e.printStackTrace();
	 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
	 * catch (IOException ex) { error = ex.getMessage(); ex.printStackTrace();
	 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
	 * catch (InvalidFormatException e) { e.printStackTrace(); } catch (Exception e)
	 * { e.printStackTrace(); }
	 * 
	 * return list; }
	 * 
	 * // xls 업로드 private List<Object> upload_xls(MultipartFile excelFile, String
	 * type) { String error = "";
	 * 
	 * List<Object> list = new ArrayList<Object>();
	 * 
	 * try {
	 * 
	 * HSSFWorkbook workBook = new HSSFWorkbook(excelFile.getInputStream());
	 * HSSFSheet sheet = workBook.getSheetAt(0);
	 * 
	 * for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	 * 
	 * HSSFRow row = sheet.getRow(i);
	 * 
	 * if (null == row) { continue; }
	 * 
	 * List<String> cellList = new ArrayList<String>(); for (int j = 0; j <
	 * row.getLastCellNum(); j++) { HSSFCell cell = row.getCell(j);
	 * cellList.add(cellReader(cell)); }
	 * 
	 * try {
	 * 
	 * if (type.equals("seed")) { Seed data = bindSeed(cellList); list.add(data); }
	 * else if(type.equals("reagent")) { Reagent data = bindReagent(cellList);
	 * list.add(data); } else if(type.equals("equipment")){ Equipment data =
	 * bindEquipment(cellList); list.add(data); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } } catch (
	 * 
	 * IllegalArgumentException e) { error = e.getMessage(); e.printStackTrace();
	 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
	 * catch (IOException ex) { error = ex.getMessage(); ex.printStackTrace();
	 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
	 * catch (Exception e) { e.printStackTrace(); } return list; }
	 */
}
