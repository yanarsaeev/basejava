package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<String> {
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

    public int checkIndex(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        return index;
    }

    @Override
    protected void doSave(String key, Resume r) {
        int index = getIndex(r.getUuid());
        if (index > 0) {
            throw new ExistStorageException(r.getUuid());
        }
        insertElement(r, index);
        size++;
    }

    @Override
    protected void doDelete(String key, Resume r) {
        int index = checkIndex(r);
        fillEmptyCell(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(String key, Resume r) {
        int index = checkIndex(r);
        storage[index] = r;
    }

    @Override
    protected Resume doGet(String key, Resume r) {
        int index = checkIndex(r);
        return storage[index];
    }

    @Override
    protected boolean isExisting(String key) {
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
