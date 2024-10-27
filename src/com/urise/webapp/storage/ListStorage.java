package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage<T> extends AbstractStorage<T> {
    List<Resume> list = new LinkedList<>();

    @Override
    public void clear() {
        List<Resume> copyList = new LinkedList<>(list);
        list.removeAll(copyList);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected void doSave(T key, Resume r) {
        list.add(r);
    }

    @Override
    protected void doDelete(T key, Resume r) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(r.getUuid())) {
                list.remove(list.get(i));
                break;
            }
        }
    }

    @Override
    protected Resume doGet(T key, Resume r) {
        for (Resume resume : list) {
            if (resume.getUuid().equals(r.getUuid())) {
                return resume;
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(T key, Resume r) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(r.getUuid())) {
                list.set(i, r);
                break;
            }
        }
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(Resume[]::new);
     }

    @Override
    protected boolean isExisting(T key) {
        for (Resume resume : list) {
            if (resume.getUuid().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
