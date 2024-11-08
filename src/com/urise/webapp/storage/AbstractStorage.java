package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;


public abstract class AbstractStorage<T> implements Storage {
    protected abstract void doSave(T searchKey, Resume r);
    protected abstract void doDelete(T searchKey);
    protected abstract Resume doGet(T searchKey);
    protected abstract void doUpdate(T searchKey, Resume r);
    protected abstract boolean isExisting(T searchKey);
    protected abstract T getSearchKey(String uuid);

    protected abstract List<Resume> getAll();

    public T getExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public T getNotExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public Resume get(String uuid) {
        T key = getExistingSearchKey(uuid);
        return doGet(key);
    }

    public void update(Resume r) {
        T key = getExistingSearchKey(r.getUuid());
        doUpdate(key, r);
    }

    public void save(Resume r) {
        T key = getNotExistingSearchKey(r.getUuid());
        doSave(key, r);
    }

    public void delete(String uuid) {
        T key = getExistingSearchKey(uuid);
        doDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
        List<Resume> list = getAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }
}
