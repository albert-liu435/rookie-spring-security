package com.rookie.bigdata.basic.md;

/**
 * @Class MD5Hash
 * @Description https://www.cnblogs.com/xiaxveliang/p/15004954.html
 * @Author rookie
 * @Date 2024/7/23 11:50
 * @Version 1.0
 */
public class MD5Hash {

    /**
     * RFC1321中定义的标准4*4矩阵的常量:循环位移常量数据 s
     */
    static final int S11 = 7, S12 = 12, S13 = 17, S14 = 22;
    static final int S21 = 5, S22 = 9, S23 = 14, S24 = 20;
    static final int S31 = 4, S32 = 11, S33 = 16, S34 = 23;
    static final int S41 = 6, S42 = 10, S43 = 15, S44 = 21;

    /**
     * 填充数据 1000 0000 0000 ...
     * 长度：64*8 = 512bit
     * 注：-128为1000 0000
     */
    static final byte[] PADDING =
            {
                    -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0
            };
    /**
     * a、b、c、d 四个变量
     */
    private long[] abcd = new long[4];

    /**
     * 512字节分组数据缓冲 64*8=512bit
     */
    private byte[] buffer512Bit = new byte[64];
    // 输入数据的位长信息（64bit）
    private long[] inputBitCount = new long[2];


    /**
     * MD5计算结果
     */
    // MD5计算结果：16 * 8bit = 128bit
    public byte[] md5ByteArray = new byte[16];
    // MD5计算结果：字符串表示的MD5计算结果
    public String md5ResultStr;


    /**
     * 调用其可对任意字符串进行加密运算，并以字符串形式返回加密结果。
     *
     * @param inputStr 输入字符串
     * @return 输入md5计算结果
     */
    public String getMD5(String inputStr) {
        // 数据初始化A、B、C、D
        md5Init();
        // 调用MD5的主计算过程
        md5Update(inputStr.getBytes(), inputStr.length());
        // 输出结果到digest数组中
        md5Final();
        // 转化为16进制字符串
        for (int i = 0; i < 16; i++) {
            md5ResultStr += byte2HEX(md5ByteArray[i]);
        }
        return md5ResultStr;
    }

    // #######################################################

    /**
     * 构造方法：初始化MD5核心变量
     */
    public MD5Hash() {
        md5Init();
    }

    /**
     * 初始化MD5核心变量
     */
    private void md5Init() {
        // 定义state为RFC1321中定义的标准幻数
        abcd[0] = 0x67452301L;
        abcd[1] = 0xefcdab89L;
        abcd[2] = 0x98badcfeL;
        abcd[3] = 0x10325476L;
        // 初始化 输入数据的位长信息
        inputBitCount[0] = inputBitCount[1] = 0L;

        // MD5计算结果：初始化digest数组元素为0
        for (int i = 0; i < 16; i++) {
            md5ByteArray[i] = 0;
        }
        // MD5计算结果：初始化resultStr
        md5ResultStr = "";
    }


    /**
     * MD5的主计算过程:
     *
     * @param inputByte    输入数据字节流
     * @param inputByteLen 输入数据字节长度
     */
    private void md5Update(byte[] inputByte, int inputByteLen) {
        int i, index, partLen;

        // 分配64个字节分组缓冲区：64*8bit = 512bit
        byte[] blockByteArray = new byte[64];

        // 添加inputByte信息前输入信息 字节长度（取低6位）
        index = (int) (inputBitCount[0] >>> 3) & 0x3F;
        System.out.println("index: " + index);
        // 添加inputByteLen信息后，最新的输入信息 位长
        // (inputByteLen << 3) = (inputByteLen * 8) 为输入数据位长（bit length）
        if ((inputBitCount[0] += (inputByteLen << 3)) < (inputByteLen << 3)) {
            inputBitCount[1]++;
        }
        // 0
        inputBitCount[1] += (inputByteLen >>> 29);

        // 差多少满512bit（64字节）
        partLen = 64 - index;

        if (inputByteLen >= partLen) {
            // 拷贝 partLen 字节数据
            // 数据运算
            md5Memcpy(buffer512Bit, inputByte, index, 0, partLen);
            md5Transform(buffer512Bit);
            //
            for (i = partLen; i + 63 < inputByteLen; i += 64) {
                // 拷贝64字节数据
                // 数据运算
                md5Memcpy(blockByteArray, inputByte, 0, i, 64);
                md5Transform(blockByteArray);
            }
            index = 0;
        } else {
            i = 0;
        }
        // 拷贝64字节数据
        md5Memcpy(buffer512Bit, inputByte, index, i, inputByteLen - i);
    }

    /**
     * 整理和填写输出结果，结果放到数组digest中。
     */
    private void md5Final() {
        /**
         * 64位输入数据的位长信息（bit length）
         * 数组中：低位在前，高位在后
         */
        // 8个字节 缓存空间 8 * 8bit = 64bit
        byte[] bits = new byte[8];
        longArray2ByteArray(bits, inputBitCount, 8);

        // 输入信息的字节数byte length
        int index = (int) (inputBitCount[0] >>> 3) & 0x3f;
        // 输入信息的位长<448,补充到448；输入信息的位长>=448,补充到（512+448）= 960，960/8=120字节
        // (56 * 8bit = 448bit)
        int padLen = (index < 56) ? (56 - index) : (120 - index);

        /**
         * 数据填充： N * 512 + 448
         */
        md5Update(PADDING, padLen);
        /**
         * 1、数据填充： N * 512 + 448 + 64
         * 2、计算最后一个分组数据
         */
        md5Update(bits, 8);

        /**
         * 根据a、b、c、d 得到最终的 md5ByteArray 数据结果
         * 数组结果中（低位在前，高位在后）
         */
        longArray2ByteArray(md5ByteArray, abcd, 16);
    }

    /**
     * MD5核心变换计算程序: 由md5Update函数调用，block是分块的原始字节数组
     *
     * @param blockByteArray 512bit分组数据，分为16个子分组，每个分组32bit数据
     */
    private void md5Transform(byte blockByteArray[]) {
        // 初始化a、b、c、d
        long a = abcd[0], b = abcd[1], c = abcd[2], d = abcd[3];
        // 512bit分成16个子分组,每个分组32bit
        // 16 * 32bit = 512bit
        long[] M32 = new long[16];
        byteArray2LongArray(M32, blockByteArray, 64);
        // 进行4级级联运算
        // 第1级
        a = FF(a, b, c, d, M32[0], S11, 0xd76aa478L); /* 1 */
        d = FF(d, a, b, c, M32[1], S12, 0xe8c7b756L); /* 2 */
        c = FF(c, d, a, b, M32[2], S13, 0x242070dbL); /* 3 */
        b = FF(b, c, d, a, M32[3], S14, 0xc1bdceeeL); /* 4 */
        a = FF(a, b, c, d, M32[4], S11, 0xf57c0fafL); /* 5 */
        d = FF(d, a, b, c, M32[5], S12, 0x4787c62aL); /* 6 */
        c = FF(c, d, a, b, M32[6], S13, 0xa8304613L); /* 7 */
        b = FF(b, c, d, a, M32[7], S14, 0xfd469501L); /* 8 */
        a = FF(a, b, c, d, M32[8], S11, 0x698098d8L); /* 9 */
        d = FF(d, a, b, c, M32[9], S12, 0x8b44f7afL); /* 10 */
        c = FF(c, d, a, b, M32[10], S13, 0xffff5bb1L); /* 11 */
        b = FF(b, c, d, a, M32[11], S14, 0x895cd7beL); /* 12 */
        a = FF(a, b, c, d, M32[12], S11, 0x6b901122L); /* 13 */
        d = FF(d, a, b, c, M32[13], S12, 0xfd987193L); /* 14 */
        c = FF(c, d, a, b, M32[14], S13, 0xa679438eL); /* 15 */
        b = FF(b, c, d, a, M32[15], S14, 0x49b40821L); /* 16 */

        // 第2级
        a = GG(a, b, c, d, M32[1], S21, 0xf61e2562L); /* 17 */
        d = GG(d, a, b, c, M32[6], S22, 0xc040b340L); /* 18 */
        c = GG(c, d, a, b, M32[11], S23, 0x265e5a51L); /* 19 */
        b = GG(b, c, d, a, M32[0], S24, 0xe9b6c7aaL); /* 20 */
        a = GG(a, b, c, d, M32[5], S21, 0xd62f105dL); /* 21 */
        d = GG(d, a, b, c, M32[10], S22, 0x2441453L); /* 22 */
        c = GG(c, d, a, b, M32[15], S23, 0xd8a1e681L); /* 23 */
        b = GG(b, c, d, a, M32[4], S24, 0xe7d3fbc8L); /* 24 */
        a = GG(a, b, c, d, M32[9], S21, 0x21e1cde6L); /* 25 */
        d = GG(d, a, b, c, M32[14], S22, 0xc33707d6L); /* 26 */
        c = GG(c, d, a, b, M32[3], S23, 0xf4d50d87L); /* 27 */
        b = GG(b, c, d, a, M32[8], S24, 0x455a14edL); /* 28 */
        a = GG(a, b, c, d, M32[13], S21, 0xa9e3e905L); /* 29 */
        d = GG(d, a, b, c, M32[2], S22, 0xfcefa3f8L); /* 30 */
        c = GG(c, d, a, b, M32[7], S23, 0x676f02d9L); /* 31 */
        b = GG(b, c, d, a, M32[12], S24, 0x8d2a4c8aL); /* 32 */

        // 第3级
        a = HH(a, b, c, d, M32[5], S31, 0xfffa3942L); /* 33 */
        d = HH(d, a, b, c, M32[8], S32, 0x8771f681L); /* 34 */
        c = HH(c, d, a, b, M32[11], S33, 0x6d9d6122L); /* 35 */
        b = HH(b, c, d, a, M32[14], S34, 0xfde5380cL); /* 36 */
        a = HH(a, b, c, d, M32[1], S31, 0xa4beea44L); /* 37 */
        d = HH(d, a, b, c, M32[4], S32, 0x4bdecfa9L); /* 38 */
        c = HH(c, d, a, b, M32[7], S33, 0xf6bb4b60L); /* 39 */
        b = HH(b, c, d, a, M32[10], S34, 0xbebfbc70L); /* 40 */
        a = HH(a, b, c, d, M32[13], S31, 0x289b7ec6L); /* 41 */
        d = HH(d, a, b, c, M32[0], S32, 0xeaa127faL); /* 42 */
        c = HH(c, d, a, b, M32[3], S33, 0xd4ef3085L); /* 43 */
        b = HH(b, c, d, a, M32[6], S34, 0x4881d05L); /* 44 */
        a = HH(a, b, c, d, M32[9], S31, 0xd9d4d039L); /* 45 */
        d = HH(d, a, b, c, M32[12], S32, 0xe6db99e5L); /* 46 */
        c = HH(c, d, a, b, M32[15], S33, 0x1fa27cf8L); /* 47 */
        b = HH(b, c, d, a, M32[2], S34, 0xc4ac5665L); /* 48 */

        // 第4级
        a = II(a, b, c, d, M32[0], S41, 0xf4292244L); /* 49 */
        d = II(d, a, b, c, M32[7], S42, 0x432aff97L); /* 50 */
        c = II(c, d, a, b, M32[14], S43, 0xab9423a7L); /* 51 */
        b = II(b, c, d, a, M32[5], S44, 0xfc93a039L); /* 52 */
        a = II(a, b, c, d, M32[12], S41, 0x655b59c3L); /* 53 */
        d = II(d, a, b, c, M32[3], S42, 0x8f0ccc92L); /* 54 */
        c = II(c, d, a, b, M32[10], S43, 0xffeff47dL); /* 55 */
        b = II(b, c, d, a, M32[1], S44, 0x85845dd1L); /* 56 */
        a = II(a, b, c, d, M32[8], S41, 0x6fa87e4fL); /* 57 */
        d = II(d, a, b, c, M32[15], S42, 0xfe2ce6e0L); /* 58 */
        c = II(c, d, a, b, M32[6], S43, 0xa3014314L); /* 59 */
        b = II(b, c, d, a, M32[13], S44, 0x4e0811a1L); /* 60 */
        a = II(a, b, c, d, M32[4], S41, 0xf7537e82L); /* 61 */
        d = II(d, a, b, c, M32[11], S42, 0xbd3af235L); /* 62 */
        c = II(c, d, a, b, M32[2], S43, 0x2ad7d2bbL); /* 63 */
        b = II(b, c, d, a, M32[9], S44, 0xeb86d391L); /* 64 */

        //分别累加到 a[0], b[1], c[2], d[3]
        abcd[0] += a;
        abcd[1] += b;
        abcd[2] += c;
        abcd[3] += d;
    }


    // #######################################################

    //定义F G H I 为4个基数 ，即为4个基本的MD5函数,进行简单的位运算
    private long F(long x, long y, long z) {
        return (x & y) | ((~x) & z);
    }

    private long G(long x, long y, long z) {
        return (x & z) | (y & (~z));
    }

    private long H(long x, long y, long z) {
        return x ^ y ^ z;
    }

    private long I(long x, long y, long z) {
        return y ^ (x | (~z));
    }

    // FF,GG,HH和II调用F,G,H,I函数进行进一步变换
    private long FF(long a, long b, long c, long d, long x, long s, long ac) {
        a += F(b, c, d) + x + ac;
        // 循环左移s位
        //这里long型数据右移时使用无符号右移运算符>>>
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private long GG(long a, long b, long c, long d, long x, long s, long ac) {
        a += G(b, c, d) + x + ac;
        // 循环左移s位
        //这里long型数据右移时使用无符号右移运算符>>>
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private long HH(long a, long b, long c, long d, long x, long s, long ac) {
        a += H(b, c, d) + x + ac;
        // 循环左移s位
        //这里long型数据右移时使用无符号右移运算符>>>
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private long II(long a, long b, long c, long d, long x, long s, long ac) {
        a += I(b, c, d) + x + ac;
        // 循环左移s位
        //这里long型数据右移时使用无符号右移运算符>>>
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    // #######################################################


    /**
     * byte数组的块拷贝函数：将input数组中的起始位置为inpos，长度len的数据拷贝到output数组起始位置outpos处
     */
    private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos,
                           int len) {
        int i;
        for (i = 0; i < len; i++) {
            output[outpos + i] = input[inpos + i];
        }
    }

    // #######################################################


    /**
     * 把byte类型的数据转换成十六进制ASCII字符表示
     *
     * @param in
     * @return
     */
    private static String byte2HEX(byte in) {
        char[] digitStr =
                {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        'A', 'B', 'C', 'D', 'E', 'F'
                };
        char[] out = new char[2];
        out[0] = digitStr[(in >> 4) & 0x0F]; //取高4位
        out[1] = digitStr[in & 0x0F];        //取低4位
        String s = new String(out);
        return s;
    }

    /**
     * 将long型数组按顺序拆成byte型数组 （低位在前，高位在后）
     *
     * @param outputByteArray 输出byte数组
     * @param inputLongArray  输入long数组
     * @param byteLength      outputByteArray字节数组的长度
     */
    private void longArray2ByteArray(byte[] outputByteArray, long[] inputLongArray, int byteLength) {
        int i, j;
        for (i = 0, j = 0; j < byteLength; i++, j += 4) {
            // 低8位
            outputByteArray[j] = (byte) (inputLongArray[i] & 0xffL);
            // 中间8位[低]
            outputByteArray[j + 1] = (byte) ((inputLongArray[i] >>> 8) & 0xffL);
            // 中间8位[高]
            outputByteArray[j + 2] = (byte) ((inputLongArray[i] >>> 16) & 0xffL);
            // 高8位
            outputByteArray[j + 3] = (byte) ((inputLongArray[i] >>> 24) & 0xffL);
        }
    }

    /**
     * 将byte型数组按顺序合成long型数组，长度为len
     *
     * @param output
     * @param input
     * @param len
     */
    private void byteArray2LongArray(long[] output, byte[] input, int len) {
        int i, j;
        for (i = 0, j = 0; j < len; i++, j += 4) {
            output[i] = byte2Long(input[j])
                    | (byte2Long(input[j + 1]) << 8)
                    | (byte2Long(input[j + 2]) << 16)
                    | (byte2Long(input[j + 3]) << 24);
        }
    }

    /**
     * 把byte型数据转换为无符号long型数据
     *
     * @param b
     * @return
     */
    private static long byte2Long(byte b) {
        return b > 0 ? b : (b & 0x7F + 128);
    }
}


