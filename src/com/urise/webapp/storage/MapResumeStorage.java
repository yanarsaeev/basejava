package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    Map<String, Resume> map = new HashMap<>();

    @Override
    protected void doSave(Resume searchKey, Resume resume) {
        if (isExisting(searchKey)) {
            throw new ExistStorageException(searchKey.getUuid());
        }
        map.put(searchKey.getUuid(), searchKey);
    }

    @Override
    protected void doDelete(Resume searchKey, Resume resume) {
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(searchKey.getUuid());
        }
        map.remove(searchKey.getUuid());
    }

    @Override
    protected Resume doGet(Resume searchKey, Resume resume) {
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(searchKey.getUuid());
        }
        return map.get(searchKey.getUuid());
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(searchKey.getUuid());
        }
        map.put(searchKey.getUuid(), searchKey);
    }

    @Override
    protected boolean isExisting(Resume searchKey) {
        return map.containsValue(searchKey);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return map.size();
    }
}
