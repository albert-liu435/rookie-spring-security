package com.rookie.bigdata.basic.nationalpassword.sign;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

import lombok.Data;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.SM2KeyExchange;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.SM2KeyExchangePrivateParameters;
import org.bouncycastle.crypto.params.SM2KeyExchangePublicParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;

/**
 * @Class SM2KeyExchangeUtil
 * @Description
 * @Author rookie
 * @Date 2024/7/24 9:26
 * @Version 1.0
 */
public class SM2KeyExchangeUtil {


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
     * 生成ECC密钥对
     *
     * @return ECC密钥对
     */
    public static AsymmetricCipherKeyPair generateKeyPair() {
        SecureRandom random = new SecureRandom();
        return generateKeyPair(DOMAIN_PARAMS, random);
    }

    /**
     * @param initiator         true表示发起方，false表示响应方
     * @param keyBits           生成的密钥长度
     * @param selfStaticPriv    己方固定私钥
     * @param selfEphemeralPriv 己方临时私钥
     * @param selfId            己方ID
     * @param otherStaticPub    对方固定公钥
     * @param otherEphemeralPub 对方临时公钥
     * @param otherId           对方ID
     * @return 返回协商出的密钥，但是这个密钥是没有经过确认的
     */
    public static byte[] calculateKey(boolean initiator, int keyBits, ECPrivateKeyParameters selfStaticPriv,
                                      ECPrivateKeyParameters selfEphemeralPriv, byte[] selfId, ECPublicKeyParameters otherStaticPub,
                                      ECPublicKeyParameters otherEphemeralPub, byte[] otherId) {
        SM2KeyExchange exch = new SM2KeyExchange();
        exch.init(new ParametersWithID(
                new SM2KeyExchangePrivateParameters(initiator, selfStaticPriv, selfEphemeralPriv), selfId));
        return exch.calculateKey(keyBits,
                new ParametersWithID(new SM2KeyExchangePublicParameters(otherStaticPub, otherEphemeralPub), otherId));
    }

    /**
     * @param initiator         true表示发起方，false表示响应方
     * @param keyBits           生成的密钥长度
     * @param confirmationTag   确认信息，如果是响应方可以为null；如果是发起方则应为响应方的s1
     * @param selfStaticPriv    己方固定私钥
     * @param selfEphemeralPriv 己方临时私钥
     * @param selfId            己方ID
     * @param otherStaticPub    对方固定公钥
     * @param otherEphemeralPub 对方临时公钥
     * @param otherId           对方ID
     * @return
     */
    public static ExchangeResult calculateKeyWithConfirmation(boolean initiator, int keyBits, byte[] confirmationTag,
                                                              ECPrivateKeyParameters selfStaticPriv, ECPrivateKeyParameters selfEphemeralPriv, byte[] selfId,
                                                              ECPublicKeyParameters otherStaticPub, ECPublicKeyParameters otherEphemeralPub, byte[] otherId) {
        SM2KeyExchange exch = new SM2KeyExchange();
        exch.init(new ParametersWithID(
                new SM2KeyExchangePrivateParameters(initiator, selfStaticPriv, selfEphemeralPriv), selfId));
        byte[][] result = exch.calculateKeyWithConfirmation(keyBits, confirmationTag,
                new ParametersWithID(new SM2KeyExchangePublicParameters(otherStaticPub, otherEphemeralPub), otherId));
        ExchangeResult confirmResult = new ExchangeResult();
        confirmResult.setKey(result[0]);
        if (initiator) {
            confirmResult.setS2(result[1]);
        } else {
            confirmResult.setS1(result[1]);
            confirmResult.setS2(result[2]);
        }
        return confirmResult;
    }

    /**
     * @param s2
     * @param confirmationTag 实际上是发起方的s2
     * @return
     */
    public static boolean responderConfirm(byte[] s2, byte[] confirmationTag) {
        return Arrays.equals(s2, confirmationTag);
    }

    @Data
    public static class ExchangeResult {
        private byte[] key;
        // 发起方没有s1
        private byte[] s1;
        private byte[] s2;

    }
}
