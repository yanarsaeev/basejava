package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;


public abstract class AbstractStorage<T> implements Storage {
    protected abstract void doSave(T key, Resume r);
    protected abstract void doDelete(T key, Resume r);
    protected abstract Resume doGet(T key, Resume r);
    protected abstract void doUpdate(T key, Resume r);
    protected abstract boolean isExisting(T key);

    public T getExistingSearchKey(T searchKey) {
        if (isExisting(searchKey)) {
            throw new ExistStorageException(searchKey.toString());
        }
        return searchKey;
    }

    public T getNotExistingSearchKey(T searchKey) {
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(searchKey.toString());
        }
        return searchKey;
    }

    public Resume get(String uuid) {
        T key = getNotExistingSearchKey((T) uuid);
        return doGet(key, new Resume(uuid));
    }

    public void update(Resume r) {
        T key = getNotExistingSearchKey((T) r.getUuid());
        doUpdate(key, r);
    }

    public void save(Resume r) {
        T key = getExistingSearchKey((T) r.getUuid());
        doSave(key, r);
    }

    public void delete(String uuid) {
        T key = getNotExistingSearchKey((T) uuid);
        doDelete(key, new Resume(uuid));
    }
}
