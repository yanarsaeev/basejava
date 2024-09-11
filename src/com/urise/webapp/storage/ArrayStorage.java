package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume r) {
        int idx = getIndex(r.getUuid());
        if (idx == -1) {
            System.out.println("Резюме нет в БД");
        } else {
            storage[idx] = r;
        }
    }

    public void save(Resume r) {
        if (size > STORAGE_LIMIT) {
            System.out.println("Нет места в БД");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Резюме уже есть в БД");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int idx = getIndex(uuid);
        if (idx == -1) {
            return null;
        }

        return storage[idx];
    }

    public void delete(String uuid) {
        int idx = getIndex(uuid);
        if (idx == -1) {
            System.out.println("Резюме " + uuid + " нет в БД");
        } else {
            storage[idx] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
