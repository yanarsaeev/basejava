package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<String> {
    private final List<Resume> list = new LinkedList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected void doSave(String key, Resume r) {
        list.add(r);
    }

    @Override
    protected void doDelete(String key, Resume r) {
        list.remove(r);
    }

    @Override
    protected Resume doGet(String key, Resume r) {
        int index = list.indexOf(r);
        return list.get(index);
    }

    @Override
    protected void doUpdate(String key, Resume r) {
        int index = list.indexOf(r);
        list.set(index, r);
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(Resume[]::new);
     }

    @Override
    protected boolean isExisting(String key) {
        for (Resume resume : list) {
            if (resume.getUuid().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
