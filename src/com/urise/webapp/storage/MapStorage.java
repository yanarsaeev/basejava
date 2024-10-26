package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage<T> extends AbstractStorage<T> {
    Map<String, Resume> hashMap = new HashMap<>();
    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    protected void doSave(T key, Resume r) {
        if (isExisting(key)) {
            throw new ExistStorageException(key.toString());
        }
        hashMap.put((String) key, r);
    }

    @Override
    protected void doDelete(String uuid, T key) {
        if (!isExisting(key)) {
            throw new NotExistStorageException(uuid);
        }
        hashMap.remove(key);
    }

    @Override
    protected Resume doGet(String uuid, T key) {
        if (!isExisting(key)) {
            throw new NotExistStorageException(uuid);
        }
        return hashMap.get(key);
    }

    @Override
    protected void doUpdate(T key, Resume r) {
        if (!isExisting(key)) {
            throw new NotExistStorageException(key.toString());
        }
        hashMap.put((String) key, r);
    }

    @Override
    protected boolean isExisting(T key) {
        return hashMap.containsKey(key);
    }


    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[hashMap.size()];
        int id = 0;
        for (Resume r : hashMap.values()) {
            resumes[id++] = r;
        }
        return resumes;
    }

    @Override
    public int size() {
        return hashMap.size();
    }
}
