package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> hashMap = new HashMap<>();
    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    protected void saver(Object key, Resume r) {

    }

    @Override
    protected void deleter(String uuid, Object key) {

    }

    @Override
    protected Resume getter(String uuid, Object key) {
        return null;
    }

    @Override
    protected void updater(Object key, Resume r) {

    }

    @Override
    protected boolean isRepeat(String uuid, Object key) {
        return false;
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
