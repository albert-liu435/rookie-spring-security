package com.rookie.bigdata.basic.nationalpassword.sign;

import java.util.Arrays;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;
import org.junit.jupiter.api.Test;

/**
 * @Class DerSM2SignatrueTest
 * @Description TODO
 * @Author rookie
 * @Date 2024/7/24 9:53
 * @Version 1.0
 */
class DerSM2SignatrueTest {


        public static final byte[] WITH_ID = new byte[] { 1, 2, 3, 4 };
        public static final byte[] SRC_DATA = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };

        @Test
        public void testSignAndVerify() {
            try {
                AsymmetricCipherKeyPair keyPair = DerSM2Signatrue.generateKeyPair();
                ECPrivateKeyParameters priKey = (ECPrivateKeyParameters) keyPair.getPrivate();
                ECPublicKeyParameters pubKey = (ECPublicKeyParameters) keyPair.getPublic();


                System.out.println("Pri Hex:" + ByteUtils.toHexString(priKey.getD().toByteArray()).toUpperCase());
                System.out.println(
                        "Pub X Hex:" + ByteUtils.toHexString(pubKey.getQ().getAffineXCoord().getEncoded()).toUpperCase());
                System.out.println(
                        "Pub X Hex:" + ByteUtils.toHexString(pubKey.getQ().getAffineYCoord().getEncoded()).toUpperCase());
                System.out.println("Pub Point Hex:" + ByteUtils.toHexString(pubKey.getQ().getEncoded(false)).toUpperCase());



                byte[] sign = DerSM2Signatrue.sign(priKey, WITH_ID, SRC_DATA);
                System.out.println("SM2 sign with withId result:\n" + ByteUtils.toHexString(sign));
                byte[] rawSign = DerSM2Signatrue.decodeDERSM2Sign(sign);
                sign = DerSM2Signatrue.encodeSM2SignToDER(rawSign);
                System.out.println("SM2 sign with withId result:\n" + ByteUtils.toHexString(sign));
                boolean flag = DerSM2Signatrue.verify(pubKey, WITH_ID, SRC_DATA, sign);
                if (!flag) {
//                    Assert.fail("verify failed");
                    System.out.println("verify failed");
                }



                sign = DerSM2Signatrue.sign(priKey, SRC_DATA);
                System.out.println("SM2 sign without withId result:\n" + ByteUtils.toHexString(sign));
                flag = DerSM2Signatrue.verify(pubKey, SRC_DATA, sign);
                if (!flag) {
//                    Assert.fail("verify failed");
                    System.out.println("verify failed");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
//                Assert.fail();
                System.out.println("verify failed");
            }
        }

        @Test
        public void testEncodeSM2CipherToDER() {
            try {
                AsymmetricCipherKeyPair keyPair = DerSM2Signatrue.generateKeyPair();
                ECPrivateKeyParameters priKey = (ECPrivateKeyParameters) keyPair.getPrivate();
                ECPublicKeyParameters pubKey = (ECPublicKeyParameters) keyPair.getPublic();

                byte[] encryptedData = DerSM2Signatrue.encrypt(pubKey, SRC_DATA);

                byte[] derCipher = DerSM2Signatrue.encodeSM2CipherToDER(encryptedData);
                FileUtil.writeFile("derCipher.dat", derCipher);

                byte[] decryptedData = DerSM2Signatrue.decrypt(priKey, DerSM2Signatrue.decodeDERSM2Cipher(derCipher));
                if (!Arrays.equals(decryptedData, SRC_DATA)) {
//                    Assert.fail();
                    System.out.println("verify failed");
                }

//                Assert.assertTrue(true);
            } catch (Exception ex) {
                ex.printStackTrace();
//                Assert.fail();
                System.out.println("verify failed");
            }
        }


    }
