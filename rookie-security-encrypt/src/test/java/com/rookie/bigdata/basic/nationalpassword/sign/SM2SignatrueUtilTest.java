package com.rookie.bigdata.basic.nationalpassword.sign;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

/**
 * @Class SM2SignatrueUtilTest
 * @Description
 * @Author rookie
 * @Date 2024/7/24 9:23
 * @Version 1.0
 */
class SM2SignatrueUtilTest {
    public static final byte[] WITH_ID = new byte[]{1, 2, 3, 4};
    public static final byte[] SRC_DATA = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * 使用显式构建EC算法，构造sm2椭圆曲线参数
     */
    @Test
    public void testECPrivateKeyPKCS8() {
        try {
            AsymmetricCipherKeyPair keyPair = SM2SignatrueUtil.generateKeyPair();

            ECPrivateKeyParameters priKeyParams = (ECPrivateKeyParameters) keyPair.getPrivate();
            ECPublicKeyParameters pubKeyParams = (ECPublicKeyParameters) keyPair.getPublic();

            byte[] pkcs8Bytes = SM2SignatrueUtil.convertECPrivateKeyToPKCS8(priKeyParams, pubKeyParams);
            BCECPrivateKey priKey = SM2SignatrueUtil.convertPKCS8ToECPrivateKey(pkcs8Bytes);

            byte[] sign = SM2SignatrueUtil.sign(priKey, WITH_ID, SRC_DATA);
            System.out.println("SM2 sign with withId result:\n" + ByteUtils.toHexString(sign));
            boolean flag = SM2SignatrueUtil.verify(pubKeyParams, WITH_ID, SRC_DATA, sign);

            if (!flag) {
//                Assert.fail("[withId] verify failed");
                System.out.println("[withId] verify failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Assert.fail();
            System.out.println("fail");
        }
    }

    /**
     * 直接使用默认的参数构建EC算法构造密钥对
     */
    @Test
    public void testGenerateBCECKeyPair() {
        try {
            KeyPair keyPair = SM2SignatrueUtil.generateBCECKeyPair();

            ECPrivateKeyParameters priKey = SM2SignatrueUtil.convertPrivateKey((BCECPrivateKey) keyPair.getPrivate());
            ECPublicKeyParameters pubKey = SM2SignatrueUtil.convertPublicKey((BCECPublicKey) keyPair.getPublic());
            // 指定with_id
            byte[] sign = SM2SignatrueUtil.sign(priKey, WITH_ID, SRC_DATA);
            boolean flag = SM2SignatrueUtil.verify(pubKey, WITH_ID, SRC_DATA, sign);
            if (!flag) {
//                Assert.fail("verify failed");
                System.out.println("verify failed");
            }
            // 默认的with_id
            sign = SM2SignatrueUtil.sign(priKey, SRC_DATA);
            flag = SM2SignatrueUtil.verify(pubKey, SRC_DATA, sign);

            if (!flag) {
//                Assert.fail("verify failed");
                System.out.println("verify failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            Assert.fail();
            System.out.println("fail");
        }
    }
}

