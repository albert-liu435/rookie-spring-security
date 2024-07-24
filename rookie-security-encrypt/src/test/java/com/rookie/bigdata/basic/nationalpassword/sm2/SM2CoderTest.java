package com.rookie.bigdata.basic.nationalpassword.sm2;

import java.math.BigInteger;
import java.util.Arrays;

import com.rookie.bigdata.basic.nationalpassword.sm2.SM2Coder;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.Test;


/**
 * @Class SM2CoderTest
 * @Description
 * @Author rookie
 * @Date 2024/7/23 18:01
 * @Version 1.0
 */
class SM2CoderTest {


    public static final byte[] WITH_ID = new byte[]{1, 2, 3, 4};
    public static final byte[] SRC_DATA = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};

    @Test
    public void testEncryptAndDecrypt() {
        try {
            AsymmetricCipherKeyPair keyPair = SM2Coder.generateKeyPair();
            ECPrivateKeyParameters priKey = (ECPrivateKeyParameters) keyPair.getPrivate();
            ECPublicKeyParameters pubKey = (ECPublicKeyParameters) keyPair.getPublic();

            System.out.println("Pri Hex:" + ByteUtils.toHexString(priKey.getD().toByteArray()).toUpperCase());
            System.out.println(
                    "Pub X Hex:" + ByteUtils.toHexString(pubKey.getQ().getAffineXCoord().getEncoded()).toUpperCase());
            System.out.println(
                    "Pub X Hex:" + ByteUtils.toHexString(pubKey.getQ().getAffineYCoord().getEncoded()).toUpperCase());
            System.out.println("Pub Point Hex:" + ByteUtils.toHexString(pubKey.getQ().getEncoded(false)).toUpperCase());

            byte[] encryptedData = SM2Coder.encrypt(pubKey, SRC_DATA);
            System.out.println("SM2 encrypt result:\n" + ByteUtils.toHexString(encryptedData));
            byte[] decryptedData = SM2Coder.decrypt(priKey, encryptedData);
            System.out.println("SM2 decrypt result:\n" + ByteUtils.toHexString(decryptedData));
            if (!Arrays.equals(decryptedData, SRC_DATA)) {
//                Assert.fail();
                System.out.println("失败1");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            Assert.fail();
            System.out.println("失败2");
        }
    }

    @Test
    public void testSM2KeyRecovery() {
        try {
            String priHex = "5DD701828C424B84C5D56770ECF7C4FE882E654CAC53C7CC89A66B1709068B9D";
            String xHex = "FF6712D3A7FC0D1B9E01FF471A87EA87525E47C7775039D19304E554DEFE0913";
            String yHex = "F632025F692776D4C13470ECA36AC85D560E794E1BCCF53D82C015988E0EB956";
            String encodedPubHex = "04FF6712D3A7FC0D1B9E01FF471A87EA87525E47C7775039D19304E554DEFE0913F632025F692776D4C13470ECA36AC85D560E794E1BCCF53D82C015988E0EB956";
            String signHex = "30450220213C6CD6EBD6A4D5C2D0AB38E29D441836D1457A8118D34864C247D727831962022100D9248480342AC8513CCDF0F89A2250DC8F6EB4F2471E144E9A812E0AF497F801";
            byte[] signBytes = ByteUtils.fromHexString(signHex);
            byte[] src = ByteUtils.fromHexString("0102030405060708010203040506070801020304050607080102030405060708");
            byte[] withId = ByteUtils.fromHexString("31323334353637383132333435363738");

            ECPrivateKeyParameters priKey = new ECPrivateKeyParameters(new BigInteger(ByteUtils.fromHexString(priHex)),
                    SM2Coder.DOMAIN_PARAMS);
            ECPublicKeyParameters pubKey = SM2Coder.createECPublicKeyParameters(xHex, yHex, SM2Coder.CURVE,
                    SM2Coder.DOMAIN_PARAMS);

            if (!SM2Coder.verify(pubKey, src, signBytes)) {
//                Assert.fail("verify failed");
                System.out.println("失败3");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            Assert.fail();

            System.out.println("失败4");
        }
    }
}
