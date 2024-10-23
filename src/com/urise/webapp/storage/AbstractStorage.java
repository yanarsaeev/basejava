package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;


public abstract class AbstractStorage<T> implements Storage {
    protected abstract void doSave(T key, Resume r);
    protected abstract void doDelete(String uuid, T key);
    protected abstract Resume doGet(String uuid, T key);
    protected abstract void doUpdate(T key, Resume r);
    protected abstract boolean isRepeat(String uuid, T key);

    public Resume get(String uuid) {
        if (!isRepeat(uuid, (T) uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(uuid, (T) uuid);
    }

    public void update(Resume r) {
        if (!isRepeat(r.getUuid(), (T) r.getUuid())) {
            throw new NotExistStorageException(r.getUuid());
        }
        doUpdate((T) r.getUuid(), r);
    }

    public void save(Resume r) {
        if (isRepeat(r.getUuid(), (T) r.getUuid())) {
            throw new ExistStorageException(r.getUuid());
        }
        doSave((T) r.getUuid(), r);
    }

    public void delete(String uuid) {
        if (!isRepeat(uuid, (T) uuid)) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(uuid, (T) uuid);
    }
}
