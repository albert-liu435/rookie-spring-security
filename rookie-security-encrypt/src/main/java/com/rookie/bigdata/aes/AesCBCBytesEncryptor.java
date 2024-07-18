package com.rookie.bigdata.aes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @Class AesCBCBytesEncryptor
 * @Description AES+CBC
 * 需要一个随机生成的16字节IV参数，必须使用SecureRandom生成。因为多了一个IvParameterSpec实例，因此，初始化方法需要调用Cipher的一个重载方法并传入IvParameterSpec。每次生成的IV不同，密文也不同。
 * @Author rookie
 * @Date 2024/7/18 16:04
 * @Version 1.0
 */
public class AesCBCBytesEncryptor {


    /**
     * 加密
     *
     * @param key   加密key
     * @param input 要加密的数据
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        // 设置算法/工作模式CBC/填充
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // 恢复秘钥对象
        SecretKey keySpec = new SecretKeySpec(key, "AES");

        // CBC模式需要生成一个16 bytes的initialization vector:
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        System.out.println("iv字节数组(内容)：" + Arrays.toString(iv));
        System.out.println("iv字节数组(长度)：" + iv.length);

        // 初始化秘钥:操作模式、秘钥、IV参数
        IvParameterSpec ivps = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);

        // 加密
        byte[] data = cipher.doFinal(input);

        // IV不需要保密，把IV和密文一起返回:
        return join(iv, data);


    }

    /**
     * 解密
     *
     * @param key   解密key
     * @param input 要解密的数据
     * @return
     * @throws GeneralSecurityException
     */
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        // 把input分割成IV和密文:
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];

        System.arraycopy(input, 0, iv, 0, 16);    //IV
        System.arraycopy(input, 16, data, 0, data.length);   //密文
        System.out.println(Arrays.toString(iv));

        // 解密:
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //密码对象
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");  //恢复密码
        IvParameterSpec ivps = new IvParameterSpec(iv);    //恢复IV

        // 初始化秘钥:操作模式、秘钥、IV参数
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);

        // 解密操作
        return cipher.doFinal(data);
    }


    public static byte[] join(byte[] bs1, byte[] bs2) {
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return r;
    }

}
