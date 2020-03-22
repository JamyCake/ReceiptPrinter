package com.jamycake.printer;

import java.io.*;

public class ReceiptContentProvider {
    private static final int BUFFER_SIZE = 2048;
    private static final char END_OF_FILE = '~';
    private static final String EMPTY_STRING = "";

    private String charset;
    private File receipt;

    public ReceiptContentProvider(final String path) throws FileNotFoundException {
        this.receipt = new File(path);
        if (!receipt.exists()) throw new FileNotFoundException(receipt.getAbsolutePath());
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getReceiptContent(){
        try {
            return encodeToKOI8R();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String encodeToKOI8R() throws Exception {

        byte [] rawBytes = readRawBytesFromFile(receipt);
        String result = getResult(rawBytes);
        result.replaceAll("\\x00{4,}", EMPTY_STRING);
        return result;
    }

    private String getResult(byte[] rawBytes) throws UnsupportedEncodingException {
        String result;
        if (charset != null){
            result = new String(rawBytes, charset);
        } else {
            result = new String(rawBytes);
        }
        return result;
    }

    private byte[] readRawBytesFromFile(final File file) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        final FileInputStream inputStream = new FileInputStream(file);

        for (int i = 0; i < BUFFER_SIZE; ++i) {
            final byte byteValue = (byte) inputStream.read();
            if (byteValue == END_OF_FILE) {
                break;
            }
            buffer[i] = byteValue;
        }

        return buffer;
    }

}