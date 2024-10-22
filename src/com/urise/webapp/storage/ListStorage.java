package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage implements Storage {
    List<Resume> list = new LinkedList<>();

    @Override
    public void clear() {
        List<Resume> copyList = new LinkedList<>(list);
        list.removeAll(copyList);
    }

    @Override
    public void update(Resume r) {
        if (getElement(r.getUuid()) == null) {
            throw new NotExistStorageException(r.getUuid());
        }
        list.set(getIndex(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        if (getElement(r.getUuid()) != null) {
            throw new ExistStorageException(r.getUuid());
        }
        list.add(r);
    }

    @Override
    public Resume get(String uuid) {
        if (getIndex(uuid) == -1) {
            throw new NotExistStorageException(uuid);
        }
        return list.get(getIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        if (getElement(uuid) == null) {
            throw new NotExistStorageException(uuid);
        }
        list.remove(getElement(uuid));
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(Resume[]::new);
     }

    @Override
    public int size() {
        return list.size();
    }

    public Resume getElement(String uuid) {
        Iterator<Resume> iterator = list.listIterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        return null;
    }

    public int getIndex(String uuid) {
        Iterator<Resume> iterator = list.listIterator();
        int count = 0;
        int index = -1;
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                index = count;
            }
            count++;
        }
        return index;
    }
}
