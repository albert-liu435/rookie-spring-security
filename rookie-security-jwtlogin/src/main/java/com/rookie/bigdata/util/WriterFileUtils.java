package com.rookie.bigdata.utils;



import java.io.*;

/**
 * @Class WriterFileUtils
 * @Description https://www.cnblogs.com/zhaosq/p/10907348.html
 * @Author rookie
 * @Date 2024/7/24 16:10
 * @Version 1.0
 */
public class WriterFileUtils {


    public static String filePath(String directory, String fileName) {
        String rootPath = System.getProperty("user.dir");

        String path = rootPath + directory + fileName;
        return path;
    }


    public static InputStream readFile(String directory, String fileName) throws IOException {

        String rootPath = System.getProperty("user.dir");

        String path = rootPath + directory;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String filePath = path + fileName;
        File file = new File(filePath);

        if (file.exists()) {
//            file.delete();
            return new FileInputStream(file);
        }

        return null;
//        if (!file.exists()) {
//            file.createNewFile();
//        }


    }


    public static String writeFile(String directory, String fileName) throws IOException {

        String rootPath = System.getProperty("user.dir");

        String path = rootPath + directory;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String filePath = path + fileName;
        File file = new File(filePath);

        if (file.exists()) {
            file.delete();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        return filePath;

    }


    private static final String prefix = "classpath:";


    public static void writeFile(String directory, String fileName, String content) {
        directory = prefix + directory;

        try {

            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String filePath = directory + fileName;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath);

            fw.write(content);
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

