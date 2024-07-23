package com.rookie.bigdata.basic.sha;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @Class Sha256
 * @Description 使用SHA-256对消息进行哈希处理
 * @Author rookie
 * @Date 2024/7/23 13:45
 * @Version 1.0
 */
public class Sha256 {
    //8个初始哈希值
    //SHA256算法中用到了8个哈希初值以及64个哈希常量,这些初值是对自然数中前8个质数（2,3,5,7,11,13,17,19）的平方根的小数部分取前32bit而来
    private static final int[] H = {
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};


    //和8个哈希初值类似，这些常量是对自然数中前64个质数(2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97…)的立方根的小数部分取前32bit而来。
    private static final int[] KL={
            0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,
            0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,
            0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,
            0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,
            0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,
            0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,
            0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,
            0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2};



    /**
     * 对内容进行SHA-256加密
     * @param msg
     * @return
     */
    public static String encrypt(String msg){
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        return encrypt(bytes);
    }
    /**
     * 对内容进行SHA-256加密
     * @param filePath
     * @return
     */
    public static String encryptFile(String filePath) {

        InputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return encrypt(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String encrypt(byte[] bytes)  {
        //h 复制初始化哈希值
        int[] h = Arrays.copyOf(H,H.length);
        // k复制初始化常量
        int[] k = Arrays.copyOf(KL,KL.length);
        //消息变成二进制后的长度
        long fileSize=bytes.length*8L;
        int mod=(int) (fileSize%512);
        //计算补位后的长度
        long length = fileSize+(mod<448?(448-mod):448+(512-mod))+(512-448);
        //计算分组数量，以512位为单位分成N块，
        long group_num = length/512;

        //遍历消息块
        for(int i=0;i<group_num;i++){
            //
            int[] m = new int[64];
            boolean flag = false;
            //分成64组，每组32-bit
            for(int j=64*i,n=0;j<64*(i+1);j++,n++){
                if(j < bytes.length){
                    //第k组
                    m[n] = bytes[j]&0xff;
                }else{
                    //补位开始，第一位补1，后面补0
                    if(!flag){
                        m[n] = 0b10000000;
                        flag = true;
                    }
                    //后64位，补原始消息二进制的长度
                    if(j== 64*(i+1)-1){
                        //转成二进制
                        String bin = fill_zero(Long.toBinaryString(fileSize),32);
                        m[n-3] = Integer.parseInt(bin.substring(0,bin.length()-24),2);
                        m[n-2] = Integer.parseInt(bin.substring(bin.length()-24,bin.length()-16),2);
                        m[n-1] = Integer.parseInt(bin.substring(bin.length()-16,bin.length()-8),2);
                        m[n] = Integer.parseInt(bin.substring(24),2);
                    }
                    //补位结束
                }
            }
            //循环内的 摘要计算
            calculate_sha_256(h, k, m);
        }
        //将h1...h8转16进制字符串 拼接起来就是最后的哈希结果
        StringBuilder result = new StringBuilder();
        for (int i =0;i<h.length;i++){
            result.append(fill_zero(Integer.toHexString((int) h[i]),8));
        }
        return result.toString();
    }




    private static int[] get64W( int[] cw2) {
        int[] w = new int[64];
        for(int i=0;i<64;i++) {
            if(i<16){
                int startIndex = i * 4;
                w[i] =(int)(cw2[startIndex]) << 24 | (cw2[startIndex + 1]) << 16 | (cw2[startIndex + 2]) << 8 | cw2[startIndex + 3];
            }else{
                //W[i] = σ1(W[i−2]) + w[i-7] + σ0(W[i−15]) + w[i-16]  σ是希腊字母，英文表达为sigma
                w[i] = (int)(small_sigma1(w[i-2])+ w[i-7]+ small_sigma0(w[i-15])+ w[i-16]);
            }
        }
        return w;
    }


    //按位填充函数
    private static String fill_zero(String str,int n) {
        StringBuffer str1=new StringBuffer();
        if(str.length()<n) {
            for (int i = 0; i < n - str.length(); i++) {
                str1.append('0').toString();
            }
        }
        return str1.toString()+str;
    }

    /**
     * 计算h0 ,h1 ...h8
     * @param h
     * @param k
     * @param m
     */
    private static void calculate_sha_256(int[] h, int[] k,int [] m) {
        //1、用第(i-1)个中间哈希值来对a,b,c,d,e,f,g,h进行初始化,当i=1时，就使用初始化哈希
        int A = h[0];
        int B = h[1];
        int C = h[2];
        int D = h[3];
        int E = h[4];
        int F = h[5];
        int G = h[6];
        int H = h[7];

        //2、应用SHA256压缩函数来更新a,b,....h
        int temp1=0;
        int temp2=0;
        //计算扩展消息w0,w1,...w63
        int[] w = get64W(m);
        for(int i=0;i<64;i++) {
            temp1=T1(H,E,ch(E,F,G),w[i],k[i]);
            temp2=temp1+T2(A,maj(A,B,C));
            H=G;
            G=F;
            F=E;
            E=D+temp1;
            D=C;
            C=B;
            B=A;
            A=temp2;
        }

        //3、计算第i个中间哈希值H(i)
        h[0]= A+h[0];
        h[1]= B+h[1];
        h[2]= C+h[2];
        h[3]= D+h[3];
        h[4]= E+h[4];
        h[5]= F+h[5];
        h[6]= G+h[6];
        h[7]= H+h[7];

    }



    /**
     * ch(x,y,z)=(x∧y)⊕(¬x∧z)
     * @param x
     * @param y
     * @param z
     * @return
     */
    private static int ch(int x,int y,int z) {
        return (x&y)^(~x&z);
    }


    /**
     * maj(x,y,z)=(x∧y)⊕(x∧z)⊕(y∧z)
     * @param x
     * @param y
     * @param z
     * @return
     */
    private static int maj(int x,int y,int z) {
        return (x&y)^(x&z)^(y&z);
    }



    /**
     * ∑0(x)=s2(x)+s13(x)+s22(x)
     * s2表示右旋2位
     * @param x
     * @return
     */
    private static long big_sigma0(int x) {
        return rightRotate(x,2) ^ rightRotate(x,13) ^ rightRotate(x,22);
    }


    /**
     * ∑1(x)=s6(x)+s11(x)+s25(x)
     * s6表示右旋6位
     * @param x
     * @return
     */
    private static long big_sigma1(long x) {
        return rightRotate(x,6) ^ rightRotate(x,11) ^ rightRotate(x,25);
    }


    /**
     * σ0(x)=s7(x)+s18(x)+r3(x)
     * s7表示右旋7位
     * r3表示右移3位
     * @param x
     * @return
     */
    private static long small_sigma0(int x) {
        return rightRotate(x,7) ^ rightRotate(x,18) ^ rightShift(x,3);
    }


    /**
     * σ1(x)=s17(x)+s19(x)+r10(x)
     * s17表示右旋17位
     * r10表示右移10位
     * @param x
     * @return
     */
    private static long small_sigma1(int x) {
        return rightRotate(x,17) ^ rightRotate(x,19) ^ rightShift(x,10);
    }




    /**
     * 计算T1
     * T1= h + ∑1(e) + ch(e,f,g) + w + k
     * @param h
     * @param e
     * @param ch
     * @param w
     * @param k
     * @return
     */
    private static int T1(int h,int e,int ch,int w,int k) {
        int num =(int)(h+ big_sigma1(e)+ch+w+k);
        return num;
    }


    /**
     * 计算T2
     * T2 = ∑0(a) + maj(a,b,c)
     * @param a
     * @param maj
     * @return
     */
    private static int T2(int a,int maj) {
        int num = (int)(big_sigma0(a)+maj);
        return num;
    }

    /**
     * 右旋n位
     * @param x
     * @param n
     * @return
     */
    private static long rightRotate(long x, int n)
    {
        long wei=(0<<n)-1;
        x = ((wei&(x&0xffffffffL))<<(32-n))|(x&0xffffffffL)>>n;
        return x;

    }
    /**
     * 按位右移n位
     * @param x
     * @param n
     * @return
     */
    private static long rightShift(int x, int n) {
        return (x&0xFFFFFFFFL)>>n;
    }


}
