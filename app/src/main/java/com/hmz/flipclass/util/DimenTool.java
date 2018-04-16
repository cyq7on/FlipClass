package com.hmz.flipclass.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Created by sstang on 2018/2/2.
 */

public class DimenTool {
    public final static int defaultDp = 360;

    public static void main(String[] args) {
        Davn();
    }

    public static void Davn() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("./app/src/main/res/values/dimen.xml");

        BufferedReader reader = null;

        StringBuilder sw240 = new StringBuilder();
        StringBuilder sw320 = new StringBuilder();
        StringBuilder sw360 = new StringBuilder();
        StringBuilder sw400 = new StringBuilder();
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw960 = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = roundOff(Double.parseDouble(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2)));

                    sw240.append(start).append(roundOff(num * 240 / defaultDp)).append(end).append("\r\n");
                    sw320.append(start).append(roundOff(num * 320 / defaultDp)).append(end).append("\r\n");
                    sw360.append(start).append(roundOff(num * 360 / defaultDp)).append(end).append("\r\n");
                    sw400.append(start).append(roundOff(num * 400 / defaultDp)).append(end).append("\r\n");
                    sw480.append(start).append(roundOff(num * 480 / defaultDp)).append(end).append("\r\n");
                    sw720.append(start).append(roundOff(num * 720 / defaultDp)).append(end).append("\r\n");
                    sw960.append(start).append(roundOff(num * 960 / defaultDp)).append(end).append("\r\n");
                }else{
                    sw240.append(tempString).append("\r\n");
                    sw320.append(tempString).append("\r\n");
                    sw360.append(tempString).append("\r\n");
                    sw400.append(tempString).append("\r\n");
                    sw480.append(tempString).append("\r\n");
                    sw720.append(tempString).append("\r\n");
                    sw960.append(tempString).append("\r\n");
                }
            }
            reader.close();
            File w240 = new File("./app/src/main/res/values-sw240dp/dimen.xml");
            File w320 = new File("./app/src/main/res/values-sw320dp/dimen.xml");
            File w360 = new File("./app/src/main/res/values-sw360dp/dimen.xml");
            File w400 = new File("./app/src/main/res/values-sw400dp/dimen.xml");
            File w480 = new File("./app/src/main/res/values-sw480dp/dimen.xml");
            File w720 = new File("./app/src/main/res/values-sw720dp/dimen.xml");
            File w960 = new File("./app/src/main/res/values-sw960dp/dimen.xml");

            Make(w240);
            Make(w320);
            Make(w360);
            Make(w400);
            Make(w480);
            Make(w720);
            Make(w960);

            //将新的内容，写入到指定的文件中去
            writeFile(w240, sw240.toString());
            writeFile(w320, sw320.toString());
            writeFile(w360, sw360.toString());
            writeFile(w400, sw400.toString());
            writeFile(w480, sw480.toString());
            writeFile(w720, sw720.toString());
            writeFile(w960, sw960.toString());


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(File file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    //自定义检测生成指定文件夹下的指定文件
    public static void Make(File file) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //四舍五入保留一位小数
    public static double roundOff(Double num){
        BigDecimal b = new BigDecimal(num);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
