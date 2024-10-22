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
    protected void saver(T key, Resume r) {
        list.add(r);
    }

    @Override
    protected void deleter(String uuid, T key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                list.remove(list.get(i));
                break;
            }
        }
    }

    @Override
    protected Resume getter(String uuid, T key) {
        for (Resume resume : list) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    @Override
    protected void updater(T key, Resume r) {
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
    protected boolean isRepeat(String uuid, T key) {
        for (Resume resume : list) {
            if (resume.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}
