package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public abstract class AbstractStorage<T> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
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
            LOG.warning("Резюме " + uuid + " нет в БД");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public T getNotExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            LOG.warning("Резюме " + uuid + " уже есть в БД");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        T key = getExistingSearchKey(uuid);
        return doGet(key);
    }

    public void update(Resume r) {
        LOG.info("Update " + r);
        T key = getExistingSearchKey(r.getUuid());
        doUpdate(key, r);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        T key = getNotExistingSearchKey(r.getUuid());
        doSave(key, r);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        T key = getExistingSearchKey(uuid);
        doDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }
}
