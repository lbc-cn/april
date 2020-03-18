package com.lbc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReaderUtil {
    private ArrayList<String> csvList=new ArrayList<>();
    StringBuffer stringBuffer=new StringBuffer();

    public String csvParseList(File fileName){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String line ="";
            br.readLine();
            stringBuffer.append("[");
            while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {
                String aa=line.split(",")[0];
                stringBuffer.append("\"").append(aa).append("\",");
                csvList.add(aa);
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1).append("]");
        }catch(Exception e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
