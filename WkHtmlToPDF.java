package com.haier.openplatform.cosmocg.store.util;
import java.io.File;

public class WkHtmlToPDF {

        // wkhtmltopdf在系统中的路径
        private static String packageName = WkHtmlToPDF.class.getPackage().getName().replace('.', '\\');
        private static String contentName = System.getProperty("user.dir");
        private static String toPdfTool = contentName + "\\store-biz\\src\\main\\java\\" + packageName +"\\bin\\wkhtmltopdf";


         //  srcPath：html路径或url； destPath：pdf保存路径
        public static boolean convert(String srcPath, String destPath) {

            File file = new File(destPath);
            File parent = file.getParentFile();
            // 如果pdf保存路径不存在，则创建路径
            if (!parent.exists()) {
                parent.mkdirs();
            }

            StringBuilder cmd = new StringBuilder();
            cmd.append(toPdfTool);
            cmd.append(" ");
            cmd.append("--encoding utf-8");             // 对输入文件编码
            cmd.append(" ");
            cmd.append("--page-height 450");
            cmd.append(" ");
            cmd.append("--page-width 320");
            cmd.append(" ");
            cmd.append("--margin-top 20");
            cmd.append(" ");
            cmd.append("--margin-bottom 0");
            cmd.append(" ");
            cmd.append("--margin-left 10");
            cmd.append(" ");
            cmd.append("--margin-right 10");
            cmd.append(" ");
            cmd.append("--disable-smart-shrinking");      // 不允许智能缩小
            cmd.append(" ");
            cmd.append("--enable-forms");                 // 转换HTML表单为PDF表单
            cmd.append(" ");
            cmd.append("--enable-plugins");               // 允许使用插件
            cmd.append(" ");
            cmd.append("--debug-javascript");             // 显示javascript调试输出的信息
//            cmd.append(" ");
//            cmd.append("--dpi 150");                      // 指定分辨率
            cmd.append(" ");
            cmd.append(srcPath);
            cmd.append(" ");
            cmd.append(destPath);
//            cmd.append(" ");
//            cmd.append("--javascript-delay 1000");          // 延迟加载js
//            cmd.append(" ");
//            cmd.append(" --window-status done");          // 等待异步完成
//            cmd.append(" ");
//            cmd.append("--no-pdf-compression");           // 无压缩
//            cmd.append(" ");
//            cmd.append("--footer-center [page]/[topage]");// 显示页数

            System.out.println(cmd.toString());
            boolean result = true;
            try {
                Process proc = Runtime.getRuntime().exec(cmd.toString());
                HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
                HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
                error.start();
                output.start();
                proc.waitFor();
            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            }

            return result;
        }

        public static void main(String[] args) {
            long startTime = System.currentTimeMillis();   //获取开始时间

            String url = "测试url";
            String destPath = "生成的pdf存放的位置";
            WkHtmlToPDF.convert(url, destPath);

            long endTime = System.currentTimeMillis();
            System.out.println("\n程序运行时间： "+(endTime-startTime)+"ms");
        }

}
