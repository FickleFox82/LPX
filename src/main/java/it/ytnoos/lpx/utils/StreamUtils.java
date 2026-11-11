/*
 * Decompiled with CFR 0.152.
 */
package it.ytnoos.lpx.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
    private StreamUtils() {
    }

    public static void write(OutputStream outputStream, byte[] byArray) throws IOException {
        byte[] byArray2 = byArray;
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(byArray2.length);
        dataOutputStream.write(byArray2);
        dataOutputStream.flush();
    }

    public static byte[] readByteArray(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int n = dataInputStream.readInt();
        byte[] byArray = new byte[n];
        dataInputStream.readFully(byArray);
        return byArray;
    }
}

