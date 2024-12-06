package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serialize.StreamSerialize;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerialize streamSerialize;

    protected PathStorage(String dir, StreamSerialize streamSerialize) {
        directory = Paths.get(dir);
        this.streamSerialize = streamSerialize;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Path save error" + path.getFileName(), r.getUuid(), e);
        }
        doUpdate(path, r);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString());
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamSerialize.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path get error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            streamSerialize.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path update error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExisting(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected List<Resume> getAll() {
        return getList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return getList().toList().size();
    }

    public Stream<Path> getList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("getList error", null);
        }
    }
}
