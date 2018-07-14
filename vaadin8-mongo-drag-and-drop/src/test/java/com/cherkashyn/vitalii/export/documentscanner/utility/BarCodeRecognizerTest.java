package com.cherkashyn.vitalii.export.documentscanner.utility;

import org.junit.Assert;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class BarCodeRecognizerTest {

    @org.junit.Test
    public void recognizeOneCode() {
        // given
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("barcode-2233.png").getFile());

        // when
        List<String> codes = new BarCodeRecognizer().recognize(file);

        // then
        Assert.assertEquals(1,codes.size());
        Assert.assertEquals("2233",codes.get(0));
    }

    @org.junit.Test
    public void recognizeMultiCode() {
        // given
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("multi-barcode.jpg").getFile());

        // when
        List<String> codes = new BarCodeRecognizer().recognize(file);

        // then
        Assert.assertEquals(2,codes.size());
        Assert.assertEquals("2233",codes.get(0));
        Assert.assertEquals("ABC-abc-1234",codes.get(1));
    }

    @org.junit.Test
    public void recognizeNoCode() {
        // given
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("seahorse_seahorse.png").getFile());

        // when
        List<String> codes = new BarCodeRecognizer().recognize(file);

        // then
        Assert.assertEquals(0,codes.size());
    }

}