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
    protected void saver(T key, Resume r) {
        if (isRepeat(key.toString(), key)) {
            throw new ExistStorageException(key.toString());
        }
        hashMap.put((String) key, r);
    }

    @Override
    protected void deleter(String uuid, T key) {
        if (!isRepeat(uuid, key)) {
            throw new NotExistStorageException(uuid);
        }
        hashMap.remove(key);
    }

    @Override
    protected Resume getter(String uuid, T key) {
        if (!isRepeat(uuid, key)) {
            throw new NotExistStorageException(uuid);
        }
        return hashMap.get(key);
    }

    @Override
    protected void updater(T key, Resume r) {
        if (!isRepeat(key.toString(), key)) {
            throw new NotExistStorageException(key.toString());
        }
        hashMap.put((String) key, r);
    }

    @Override
    protected boolean isRepeat(String uuid, T key) {
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
