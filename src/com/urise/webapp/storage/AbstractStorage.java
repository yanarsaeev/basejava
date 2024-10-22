package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;


public abstract class AbstractStorage<T> implements Storage {
    protected abstract void saver(T key, Resume r);
    protected abstract void deleter(String uuid, T key);
    protected abstract Resume getter(String uuid, T key);
    protected abstract void updater(T key, Resume r);
    protected abstract boolean isRepeat(String uuid, T key);

    public Resume get(String uuid) {
        if (!isRepeat(uuid, (T) uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return getter(uuid, (T) uuid);
    }

    public void update(Resume r) {
        if (!isRepeat(r.getUuid(), (T) r.getUuid())) {
            throw new NotExistStorageException(r.getUuid());
        }
        updater((T) r.getUuid(), r);
    }

    public void save(Resume r) {
        if (isRepeat(r.getUuid(), (T) r.getUuid())) {
            throw new ExistStorageException(r.getUuid());
        }
        saver((T) r.getUuid(), r);
    }

    public void delete(String uuid) {
        if (!isRepeat(uuid, (T) uuid)) {
            throw new NotExistStorageException(uuid);
        }
        deleter(uuid, (T) uuid);
    }
}
