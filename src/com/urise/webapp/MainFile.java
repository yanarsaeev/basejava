package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    static void listFilesRecursively(File directory, int count) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                StringBuilder stringBuilder = new StringBuilder(" ".repeat(count * 2));
                for (File file : files) {
                    if (file.isDirectory()) {
                        System.out.println(stringBuilder.append(file.getName()));
                        listFilesRecursively(file, count + 1);
                    } else {
                        System.out.println(file.getName());
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File directory = new File("C:/javaops/basejava");
        listFilesRecursively(directory, 0);
    }
}
