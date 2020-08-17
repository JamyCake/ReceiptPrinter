package com.jamycake.printer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReceiptContentProvider {
    private static final int BUFFER_SIZE = 1024;
    private static final char END_OF_FILE = '~';
    private static final String EMPTY_STRING = "";
    static String CHARSET_NAME = "KOI8-R";
    private final File receipt;

    public ReceiptContentProvider(String path) throws FileNotFoundException {
        receipt = new File(path);
        if (!receipt.exists()) {
            throw new FileNotFoundException(receipt.getAbsolutePath());
        }
    }

    public String getReceiptContents() throws Exception {
        return encodeFromKOI8R();
    }

    private String encodeFromKOI8R() throws Exception {
        byte[] rawBytes = readRawBytesFromFile(this.receipt);
        return (new String(rawBytes, CHARSET_NAME))
                .replaceAll("\\x00{4,}", EMPTY_STRING);
    }

    private byte[] readRawBytesFromFile(File file) throws IOException {
        byte[] buffer = new byte[1024];
        FileInputStream inputStream = new FileInputStream(file);

        for(int i = 0; i < BUFFER_SIZE; ++i) {
            byte byteValue = (byte)inputStream.read();
            if (byteValue == END_OF_FILE) {
                break;
            }

            buffer[i] = byteValue;
        }

        return buffer;
    }
}
