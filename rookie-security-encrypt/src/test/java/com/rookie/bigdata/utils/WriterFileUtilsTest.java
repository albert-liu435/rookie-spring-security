package com.rookie.bigdata.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class WriterFileUtilsTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 16:11
 * @Version 1.0
 */
class WriterFileUtilsTest {

    @Test
    void test01() throws Exception {


        WriterFileUtils.writeFile("/src/main/resources/com/rookie/bigdata/basic/cert/","test.txt");


    }

}
