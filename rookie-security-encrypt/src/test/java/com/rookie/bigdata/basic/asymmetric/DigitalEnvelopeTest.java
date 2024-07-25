package com.rookie.bigdata.basic.asymmetric;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Class DigitalEnvelopeTest
 * @Description
 * @Author rookie
 * @Date 2024/7/25 15:09
 * @Version 1.0
 */
class DigitalEnvelopeTest {

//    @Test
//    public void generatorKeyPair() {
//        KeyPair pair = DigitalEnvelope.generatorKeyPair();
//        Thread client = new DigitalEnvelope.Client(pair);
//        Thread server = new DigitalEnvelope.Server(pair.getPublic());
//        server.start();
//        client.start();
//
//    }

    public static void main(String[] args) {
        KeyPair pair = DigitalEnvelope.generatorKeyPair();
        Thread client = new DigitalEnvelope.Client(pair);
        Thread server = new DigitalEnvelope.Server(pair.getPublic());
        server.start();
        client.start();
    }

}
