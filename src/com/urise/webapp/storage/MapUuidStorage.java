package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    Map<String, Resume> hashMap = new HashMap<>();
    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    protected void doSave(String key, Resume r) {
        if (isExisting(key)) {
            throw new ExistStorageException(r.getUuid());
        }
        hashMap.put(key, r);
    }

    @Override
    protected void doDelete(String key, Resume r) {
        if (!isExisting(key)) {
            throw new NotExistStorageException(r.getUuid());
        }
        hashMap.remove(key);
    }

    @Override
    protected Resume doGet(String key, Resume r) {
        if (!isExisting(key)) {
            throw new NotExistStorageException(r.getUuid());
        }
        return hashMap.get(key);
    }

    @Override
    protected void doUpdate(String key, Resume r) {
        if (!isExisting(key)) {
            throw new NotExistStorageException(r.getUuid());
        }
        hashMap.put(key, r);
    }

    @Override
    protected boolean isExisting(String key) {
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
