package com.urise.webapp.storage.serialize;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamPathStorage implements StreamSerialize {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        Object resume;
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            try {
                resume = ois.readObject();
            } catch (EOFException e) {
                throw new StorageException("EOF exception", null, e);
            }
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
        return (Resume) resume;
    }
}
