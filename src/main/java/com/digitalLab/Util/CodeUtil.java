package com.digitalLab.Util;

import java.time.LocalDate;
import java.util.stream.Stream;

public class CodeUtil {
	
    public static String getFullCode(String code, String itemCode , String groupCode){
    	
        String uniqueCode = getUniqueCode(code);
        // 현재 연도
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int[] yearArr = Stream.of(String.valueOf(year).split("")).mapToInt(Integer::parseInt).toArray();
        String todayYear = yearArr[2] + "" + yearArr[3];

        return itemCode + "-" + groupCode + "-"+ todayYear + "-" +uniqueCode;
    }

    private static String getUniqueCode(String code){
    	
        if (code != null) {
        	
            String[] codeArr = code.split("-");
            int endNum = Integer.parseInt(codeArr[3]);

            int changeNum = endNum;
            changeNum = changeNum + 1;
            
            code = String.format("%05d", changeNum);

        } else {
            code = "00001";
        }

        return code;
    }
}
