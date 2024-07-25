package com.rookie.bigdata.basic.asymmetric.ecc;

import java.util.Map;

/**
 * @Class BcEccAlgorithmUtil
 * @Description
 * @Author rookie
 * @Date 2024/7/25 15:25
 * @Version 1.0
 */
public class BcEccAlgorithmUtil extends AbstractBcEccAlgorithmUtil{

    public static Map.Entry<String, String> initKeyPairBase64() throws Exception {
        return initKeyPairBase64("EC");
    }
}
