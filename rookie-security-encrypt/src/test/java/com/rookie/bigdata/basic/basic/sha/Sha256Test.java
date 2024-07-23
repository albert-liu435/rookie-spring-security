package com.rookie.bigdata.basic.basic.sha;

import cn.hutool.crypto.digest.DigestUtil;
import com.rookie.bigdata.basic.basic.sha.Sha256;
import org.junit.jupiter.api.Test;

/**
 * @Class Sha256Test
 * @Description TODO
 * @Author rookie
 * @Date 2024/7/23 13:47
 * @Version 1.0
 */
class Sha256Test {


    @Test
    void test01() {
        // 测试sha256算法
        String str = "ch-happy";
        String result1 = Sha256.encrypt(str);
        String result2 = DigestUtil.sha256Hex(str);
        System.out.println(result1);
        System.out.println(result2);
    }

}
