package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage implements StreamSerialize {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {

    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return null;
    }
}
