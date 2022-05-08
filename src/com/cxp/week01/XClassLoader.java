package com.cxp.week01;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class XClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = new XClassLoader().findClass("Hello");
        clazz.getMethod("hello").invoke(clazz.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] bytes = this.getBytes();
            return this.defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getBytes() throws IOException {
        byte[] buffer = new byte[1024];
        int length = 0;
        try (FileInputStream fis = new FileInputStream("src/com/cxp/week01/Hello.xlass");
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            while ((length = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }

            byte[] bytes = baos.toByteArray();
            byte[] newBytes = new byte[bytes.length];

            for (int i = 0; i < bytes.length; i++) {
                newBytes[i] = (byte) (255 - bytes[i]);
            }

            return newBytes;
        }
    }
}
