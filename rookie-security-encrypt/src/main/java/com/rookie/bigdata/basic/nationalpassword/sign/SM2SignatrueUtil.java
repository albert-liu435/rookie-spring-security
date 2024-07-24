package com.rookie.bigdata.basic.nationalpassword.sign;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

/**
 * @Class SM2SignatrueUtil
 * @Description
 * @Author rookie
 * @Date 2024/7/24 9:22
 * @Version 1.0
 */
public class SM2SignatrueUtil {

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
    //
    private static final String ALGO_NAME_EC = "EC";
    private static final String PEM_STRING_PUBLIC = "PUBLIC KEY";
    private static final String PEM_STRING_ECPRIVATEKEY = "EC PRIVATE KEY";



    static {
        Security.addProvider(new BouncyCastleProvider());
    }


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
     * 获取密钥对
     *
     * @return
     */
    public static AsymmetricCipherKeyPair generateKeyPair() {
        SecureRandom random = new SecureRandom();
        return generateKeyPair(DOMAIN_PARAMS, random);
    }

    /**
     * 签名
     *
     * @param priKey
     * @param withId
     * @param srcData
     * @return
     * @throws CryptoException
     */
    public static byte[] sign(BCECPrivateKey priKey, byte[] withId, byte[] srcData) throws CryptoException {
        ECPrivateKeyParameters priKeyParameters = convertPrivateKey(priKey);
        return sign(priKeyParameters, withId, srcData);
    }

    /**
     * 签名
     *
     * @param priKeyParameters
     * @param withId
     *            可以为null，若为null，则默认withId为字节数组:"1234567812345678".getBytes()
     * @param srcData
     * @return
     * @throws CryptoException
     */
    public static byte[] sign(ECPrivateKeyParameters priKeyParameters, byte[] withId, byte[] srcData)
            throws CryptoException {
        SM2Signer signer = new SM2Signer();
        CipherParameters param = null;
        ParametersWithRandom pwr = new ParametersWithRandom(priKeyParameters, new SecureRandom());
        if (withId != null) {
            param = new ParametersWithID(pwr, withId);
        } else {
            param = pwr;
        }
        signer.init(true, param);
        signer.update(srcData, 0, srcData.length);
        return signer.generateSignature();
    }

    /**
     * 密钥转换
     *
     * @param ecPriKey
     * @return
     */
    public static ECPrivateKeyParameters convertPrivateKey(BCECPrivateKey ecPriKey) {
        ECParameterSpec parameterSpec = ecPriKey.getParameters();
        ECDomainParameters domainParameters = new ECDomainParameters(parameterSpec.getCurve(), parameterSpec.getG(),
                parameterSpec.getN(), parameterSpec.getH());
        return new ECPrivateKeyParameters(ecPriKey.getD(), domainParameters);
    }

    /**
     * 验证
     *
     * @param pubKeyParams
     * @param withId
     *            可以为null，若为null，则默认withId为字节数组:"1234567812345678".getBytes()
     * @param srcData
     * @param sign
     * @return
     */
    public static boolean verify(ECPublicKeyParameters pubKeyParams, byte[] withId, byte[] srcData, byte[] sign) {
        SM2Signer signer = new SM2Signer();
        CipherParameters param;
        if (withId != null) {
            param = new ParametersWithID(pubKeyParams, withId);
        } else {
            param = pubKeyParams;
        }
        signer.init(false, param);
        signer.update(srcData, 0, srcData.length);
        return signer.verifySignature(sign);
    }

    /**
     * 将ECC私钥转换为PKCS8标准的字节流
     *
     * @param priKey
     * @param pubKey
     *            可以为空，但是如果为空的话得到的结果OpenSSL可能解析不了
     * @return
     */
    public static byte[] convertECPrivateKeyToPKCS8(ECPrivateKeyParameters priKey, ECPublicKeyParameters pubKey) {
        ECDomainParameters domainParams = priKey.getParameters();
        ECParameterSpec spec = new ECParameterSpec(domainParams.getCurve(), domainParams.getG(), domainParams.getN(),
                domainParams.getH());
        BCECPublicKey publicKey = null;
        if (pubKey != null) {
            publicKey = new BCECPublicKey(ALGO_NAME_EC, pubKey, spec, BouncyCastleProvider.CONFIGURATION);
        }
        BCECPrivateKey privateKey = new BCECPrivateKey(ALGO_NAME_EC, priKey, publicKey, spec,
                BouncyCastleProvider.CONFIGURATION);
        return privateKey.getEncoded();
    }

    /**
     * 将PKCS8标准的私钥字节流转换为私钥对象
     *
     * @param pkcs8Bytes
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static BCECPrivateKey convertPKCS8ToECPrivateKey(byte[] pkcs8Key)
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(pkcs8Key);
        KeyFactory kf = KeyFactory.getInstance(ALGO_NAME_EC, BouncyCastleProvider.PROVIDER_NAME);
        return (BCECPrivateKey) kf.generatePrivate(peks);
    }

    /**
     * 获取EC密钥对
     *
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    public static KeyPair generateBCECKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        SecureRandom random = new SecureRandom();
        ECParameterSpec parameterSpec = new ECParameterSpec(CURVE, G_POINT, SM2_ECC_N, SM2_ECC_H);
        kpg.initialize(parameterSpec, random);
        return kpg.generateKeyPair();
    }

    /**
     * 公钥转换
     *
     * @param ecPubKey
     * @return
     */
    public static ECPublicKeyParameters convertPublicKey(BCECPublicKey ecPubKey) {
        ECParameterSpec parameterSpec = ecPubKey.getParameters();
        ECDomainParameters domainParameters = new ECDomainParameters(parameterSpec.getCurve(), parameterSpec.getG(),
                parameterSpec.getN(), parameterSpec.getH());
        return new ECPublicKeyParameters(ecPubKey.getQ(), domainParameters);
    }

    /**
     * 签名 with_id使用默认的
     *
     * @param priKey
     * @param srcData
     * @return
     * @throws CryptoException
     */
    public static byte[] sign(ECPrivateKeyParameters priKey, byte[] srcData) throws CryptoException {
        return sign(priKey, null, srcData);
    }

    /**
     * 验证，with_id使用默认的
     *
     * @param pubKey
     * @param srcData
     * @param sign
     * @return
     */
    public static boolean verify(ECPublicKeyParameters pubKey, byte[] srcData, byte[] sign) {
        return verify(pubKey, null, srcData, sign);

    }
}

