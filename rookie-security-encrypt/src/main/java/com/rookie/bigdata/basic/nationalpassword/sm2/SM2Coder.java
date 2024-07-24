package com.rookie.bigdata.basic.nationalpassword.sm2;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;

import com.rookie.bigdata.basic.nationalpassword.sm4.BaseUtil;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;

/**
 * @Class SM2Coder
 * @Description
 * @Author rookie
 * @Date 2024/7/23 18:00
 * @Version 1.0
 */




public class SM2Coder /*extends BaseUtil*/ {
    //
    /*
     * 以下为SM2推荐曲线参数
     */
    public static final SM2P256V1Curve CURVE = new SM2P256V1Curve();
    public final static BigInteger SM2_ECC_N = CURVE.getOrder();
    public final static BigInteger SM2_ECC_H = CURVE.getCofactor();
    public final static BigInteger SM2_ECC_GX = new BigInteger(
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
    public final static BigInteger SM2_ECC_GY = new BigInteger(
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
    public static final ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);
    public static final ECDomainParameters DOMAIN_PARAMS = new ECDomainParameters(CURVE, G_POINT, SM2_ECC_N, SM2_ECC_H);


    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    //
    /**
     * 生成ECC密钥对
     *
     * @return ECC密钥对
     */
    public static AsymmetricCipherKeyPair generateKeyPair(ECDomainParameters domainParameters, SecureRandom random) {
        ECKeyGenerationParameters keyGenerationParams = new ECKeyGenerationParameters(domainParameters, random);
        ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
        keyGen.init(keyGenerationParams);
        return keyGen.generateKeyPair();
    }

    /**
     * @return
     */
    public static AsymmetricCipherKeyPair generateKeyPair() {
        SecureRandom random = new SecureRandom();
        return generateKeyPair(DOMAIN_PARAMS, random);
    }

    /**
     * @param pubKey
     * @param srcData
     * @return
     * @throws InvalidCipherTextException
     */
    public static byte[] encrypt(ECPublicKeyParameters pubKey, byte[] srcData) throws InvalidCipherTextException {
        SM2Engine engine = new SM2Engine();
        ParametersWithRandom pwr = new ParametersWithRandom(pubKey, new SecureRandom());
        engine.init(true, pwr);
        return engine.processBlock(srcData, 0, srcData.length);
    }

    /**
     * @param priKey
     * @param encryptedData
     * @return
     * @throws InvalidCipherTextException
     */
    public static byte[] decrypt(ECPrivateKeyParameters priKey, byte[] encryptedData)
            throws InvalidCipherTextException {
        SM2Engine engine = new SM2Engine();
        engine.init(false, priKey);
        return engine.processBlock(encryptedData, 0, encryptedData.length);
    }

    public static ECPublicKeyParameters createECPublicKeyParameters(String xHex, String yHex, ECCurve curve,
                                                                    ECDomainParameters domainParameters) {
        byte[] xBytes = ByteUtils.fromHexString(xHex);
        byte[] yBytes = ByteUtils.fromHexString(yHex);
        return createECPublicKeyParameters(xBytes, yBytes, curve, domainParameters);
    }

    public static ECPublicKeyParameters createECPublicKeyParameters(byte[] xBytes, byte[] yBytes, ECCurve curve,
                                                                    ECDomainParameters domainParameters) {
        final byte uncompressedFlag = 0x04;
        byte[] encodedPubKey = new byte[1 + xBytes.length + yBytes.length];
        encodedPubKey[0] = uncompressedFlag;
        System.arraycopy(xBytes, 0, encodedPubKey, 1, xBytes.length);
        System.arraycopy(yBytes, 0, encodedPubKey, 1 + xBytes.length, yBytes.length);
        return new ECPublicKeyParameters(curve.decodePoint(encodedPubKey), domainParameters);
    }

    /**
     * ECC公钥验签 不指定withId，则默认withId为字节数组:"1234567812345678".getBytes()
     *
     * @param pubKeyParameters
     *            ECC公钥
     * @param srcData
     *            源数据
     * @param sign
     *            签名
     * @return 验签成功返回true，失败返回false
     */
    public static boolean verify(ECPublicKeyParameters pubKeyParameters, byte[] srcData, byte[] sign) {
        return verify(pubKeyParameters, null, srcData, sign);
    }

    /**
     * ECC公钥验签
     *
     * @param pubKeyParameters
     *            ECC公钥
     * @param withId
     *            可以为null，若为null，则默认withId为字节数组:"1234567812345678".getBytes()
     * @param srcData
     *            源数据
     * @param sign
     *            签名
     * @return 验签成功返回true，失败返回false
     */
    public static boolean verify(ECPublicKeyParameters pubKeyParameters, byte[] withId, byte[] srcData, byte[] sign) {
        SM2Signer signer = new SM2Signer();
        CipherParameters param;
        if (withId != null) {
            param = new ParametersWithID(pubKeyParameters, withId);
        } else {
            param = pubKeyParameters;
        }
        signer.init(false, param);
        signer.update(srcData, 0, srcData.length);
        return signer.verifySignature(sign);
    }
}
