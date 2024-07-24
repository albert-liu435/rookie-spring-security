package com.rookie.bigdata.basic.nationalpassword.sign;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Class FileUtil
 * @Description TODO
 * @Author rookie
 * @Date 2024/7/24 9:55
 * @Version 1.0
 */



public class FileUtil {
    public static void writeFile(String filePath, byte[] data) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "rw");
            raf.write(data);
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
    }

    public static byte[] readFile(String filePath) throws IOException {
        RandomAccessFile raf = null;
        byte[] data;
        try {
            raf = new RandomAccessFile(filePath, "r");
            data = new byte[(int) raf.length()];
            raf.read(data);
            return data;
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
    }
}
