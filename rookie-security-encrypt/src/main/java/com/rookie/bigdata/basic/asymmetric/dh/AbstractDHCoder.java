package com.rookie.bigdata.basic.asymmetric.dh;

import javax.crypto.*;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.AbstractMap;
import java.util.Map;

/**
 * @Class AbstractDHCoder
 * @Description
 * @Author rookie
 * @Date 2024/7/25 11:53
 * @Version 1.0
 * <p>
 * <p>
 * DH密码交换协议<br>
 * DH加密流程：<br>
 * 1.初始化DH算法密钥对：<br>
 * 1.1.发送方—>构建发送方密钥-》公布发送方密钥给接收方-》使用接收者公钥构建发送方本地密钥<br>
 * 1.2.接收方—》使用发送方密钥密钥构建接收方密钥-》公布接收者公钥给发送方—》构建接收方本地密钥<br>
 * 2.DH算法加密消息传递：<br>
 * 2.1.发送方—>使用本地密钥加密消息—》发送加密消息给接收方<br>
 * 2.2.接收方—》使用本地密钥解密消息<br>
 */
public abstract class AbstractDHCoder {

    public static final String ALGORITHM = "DH";

    /**
     * 默认密钥字节数
     */
    public static final int KEY_SIZE = 1024;

    /**
     * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。
     */

    public static final String DES = "DES";

    public static final String AES = "AES";

    public static final String DESede = "DESede";

    public static final String Blowfish = "Blowfish";


    /**
     * 初始化一方密钥对
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map.Entry<byte[], byte[]> initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        //私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

        return new AbstractMap.SimpleEntry<>(publicKey.getEncoded(), privateKey.getEncoded());

    }

    /**
     * 通过公钥初始化密钥对
     *
     * @param pubKeyBytes 公钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidAlgorithmParameterException
     */
    public static Map.Entry<byte[], byte[]> initKeyByPubKey(byte[] pubKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        //解析家访公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //由一方公钥构建另一方私钥
        DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
        keyPairGenerator.initialize(dhParamSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        //私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

        return new AbstractMap.SimpleEntry<>(publicKey.getEncoded(), privateKey.getEncoded());

    }

    public abstract void encryptBefore(String secretAlgorithm);

    public abstract Cipher getCipher(String secretAlgorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException;

    /**
     * 加密
     *
     * @param secretAlgorithm 加密算法
     * @param data            待加密的数据
     * @param publicKey       别人公钥
     * @param privateKey      自己私钥
     * @return
     */
    public byte[] encrypt(String secretAlgorithm, byte[] data, byte[] publicKey, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchProviderException {
        this.encryptBefore(secretAlgorithm);
        //生成本地密钥
        SecretKey secretKey = getSecretKey(secretAlgorithm, publicKey, privateKey);
        //数据加密
        Cipher cipher = getCipher(secretAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public abstract void decryptBefore(String secretAlgorithm);


    public byte[] decrypt(String secretAlgorithm, byte[] data, byte[] publicKey, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchProviderException {
        this.decryptBefore(secretAlgorithm);
        //生成本地密钥
        SecretKey secretKey = getSecretKey(secretAlgorithm, publicKey, privateKey);
        //数据解密
        Cipher cipher = getCipher(secretAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public abstract void getSecretKeyBefore(String secretAlgorithm);

    /**
     * 构建密钥
     *
     * @param secretAlgorithm
     * @param pubKeyBytes     公钥
     * @param priKeyBytes     私钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    private SecretKey getSecretKey(String secretAlgorithm, byte[] pubKeyBytes, byte[] priKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        this.getSecretKeyBefore(secretAlgorithm);
        //初始化公钥
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //初始化私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
        Key priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        keyAgreement.init(priKey);
        keyAgreement.doPhase(pubKey, true);
        // 生成本地密钥
        SecretKey secretKey = keyAgreement.generateSecret(secretAlgorithm);
        return secretKey;
    }
}
