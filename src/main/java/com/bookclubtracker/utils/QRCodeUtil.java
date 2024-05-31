package com.bookclubtracker.utils;

import net.glxn.qrgen.javase.QRCode;

public class QRCodeUtil {
    // Private constructor to prevent instantiation
    private QRCodeUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static byte[] generateQRCode(String secret, String accountName) {
        String totpUrl = "otpauth://totp/" + accountName + "?secret=" + secret;
        return QRCode.from(totpUrl).stream().toByteArray();
    }
}