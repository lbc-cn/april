package com.lbc.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lbc
 * @classname WriterUtil
 * @date 3:39 PM
 */
public class WriterUtil {

    public static void write(String path,String text){
        try {
            Path fpath= Paths.get(path);
            //创建文件
            if(!Files.exists(fpath)) {
                Files.createFile(fpath);
            }

        try (FileWriter writer = new FileWriter(path, true);
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(text);
            bw.write("\n");
            bw.flush();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }


//            //创建BufferedReader
//            BufferedReader bfr=Files.newBufferedReader(fpath);
//            System.out.println(bfr.readLine());
//            bfr.close();
        }catch (Exception e){

        }
    }

}
