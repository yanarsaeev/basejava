package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected void doSave(Integer searchKey, Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertElement(r, searchKey);
        size++;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        fillEmptyCell(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    protected abstract void fillEmptyCell(int index);
    protected abstract void insertElement(Resume r, int index);
}
