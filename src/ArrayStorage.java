import java.util.Arrays;

/**.
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) continue;
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        size++;
        storage[size - 1] = r;
    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume != null && uuid.equals(resume.toString())) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        int num = -1;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && uuid.equals(storage[i].toString())) {
                num = i;
                break;
            }
        }

        if (num == -1) {
            System.out.println("Введен неверный id");
            return;
        }

        storage[num] = null;
        size--;

        for (int i = num; i < storage.length - 1; i++) {
            storage[i] = storage[i + 1];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
