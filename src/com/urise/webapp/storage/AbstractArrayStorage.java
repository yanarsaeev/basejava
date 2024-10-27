package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage<T> extends AbstractStorage<T> {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    @Override
    protected void doSave(T key, Resume r) {
        int index = getIndex(r.getUuid());
        if (size > STORAGE_LIMIT) {
            System.out.println("Нет места в БД");
        } else if (index > 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertElement(r, index);
            size++;
        }
    }

    @Override
    protected void doDelete(T key, Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            fillEmptyCell(index);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected void doUpdate(T key, Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    @Override
    protected Resume doGet(T key, Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }

        return storage[index];
    }

    @Override
    protected boolean isExisting(T key) {
        if (size() > 0) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int getIndex(String uuid);
    protected abstract void fillEmptyCell(int index);
    protected abstract void insertElement(Resume r, int index);
}
