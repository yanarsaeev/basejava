package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume resume1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume resume2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume resume3 = new Resume(UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(resume2, storage.get(UUID_2));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        storage.update(resume3);
        Assert.assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateExist() throws Exception {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        Assert.assertEquals(resume2, storage.get(UUID_2));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void saveOverflow() throws Exception {
        Resume[] expected = new Resume[storage.size()];
        try {
            expected[0] = resume1;
            expected[1] = resume2;
            expected[2] = resume3;
        } catch (StorageException e) {
            Assert.fail("Переполнение");
        }
        expected[3] = new Resume("uuid4");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertNotNull(storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = storage.getAll();
        Resume[] actual = new Resume[storage.size()];
        actual[0] = resume1;
        actual[1] = resume2;
        actual[2] = resume3;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}