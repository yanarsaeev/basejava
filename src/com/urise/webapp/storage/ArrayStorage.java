package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume r) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        if (hasUuid(r.getUuid())) {
            if (name.equals(r.getUuid())) {
                System.out.println("Uuid уже занят");
                return;
            }
        }

        r.setUuid(name);
    }

    public void save(Resume r) {
        if (size > 10000) {
            System.out.println("Нет места в БД");
            return;
        }

        if (hasUuid(r.getUuid())) {
            System.out.println("Резюме уже есть в БД");
            return;
        }

        storage[++size - 1] = r;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        boolean isFound = false;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            System.out.println("Резюме " + uuid + " нет в БД");
            return;
        }

        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public boolean hasUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return true;
            }
        }
        return false;
    }
}