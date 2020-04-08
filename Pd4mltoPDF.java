package com.haier.openplatform.cosmocg.store.util;

import org.zefer.pd4ml.PD4Constants;
import java.awt.Insets;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import org.zefer.pd4ml.PD4ML;

public class HtmlToPDF {
    protected int topValue = 10;
    protected int leftValue = 10;
    protected int rightValue = 10;
    protected int bottomValue = 10;
    protected int userSpaceWidth = 1000;

    public static void main(String[] args) {
        try {
            HtmlToPDF htp = new HtmlToPDF();

            String url = "测试网址";
            String destPath = "生成的pdf要存放的位置";
            htp.doConversion(url, destPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doConversion( String url, String outputPath )
            throws InvalidParameterException, MalformedURLException, IOException {

        long startTime = System.currentTimeMillis();   //获取开始时间

        PD4ML pd4ml = new PD4ML();

        // set frame width of "virtual web browser"
        pd4ml.setHtmlWidth(userSpaceWidth);

        // choose target paper format and "rotate" it to landscape orientation
        pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));

        // define PDF page margins
        pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));

        // source HTML document also may have margins, could be suppressed this way
        // (PD4ML *Pro* feature):
        pd4ml.addStyle("BODY {margin: 0}", true);

        // 设置字体
        pd4ml.useTTF("java:fonts", true);
        pd4ml.setDefaultTTFs("SimSun", "SimSun", "SimSun");

        // 开启转换调试信息
        pd4ml.enableDebugInfo();

        // 定义输出流
        File output = new File(outputPath);
        java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

        // 进行实际转换工作
        pd4ml.render(new URL(url), fos);
        fos.close();
        System.out.println("\n" + outputPath + "\ndone." );

        //获取结束时间，计算程序运行时间
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

    }
}
