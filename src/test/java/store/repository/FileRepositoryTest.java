package store.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import store.common.exception.ResourceNotFoundException;

class TestFileRepository extends FileRepository<String> {
    public TestFileRepository(String resourcePath) {
        super(resourcePath);
    }

    @Override
    public String findAll() {
        List<String> lines = readLines();
        return String.join("\n", lines);
    }
}

class FileRepositoryTest {
    private static final String VALID_RESOURCE_PATH = "/test-data.md";
    private static final String INVALID_RESOURCE_PATH = "/non-existent.md";

    @Test
    void findAll_ValidPath_ShouldReturnFileContents() {
        TestFileRepository repository = new TestFileRepository(VALID_RESOURCE_PATH);

        String result = repository.findAll();

        assertTrue(result.contains("test line 1"));
        assertTrue(result.contains("test line 2"));
    }

    @Test
    void findAll_InvalidPath_ShouldThrowResourceNotFoundException() {
        TestFileRepository repository = new TestFileRepository(INVALID_RESOURCE_PATH);

        assertThrows(ResourceNotFoundException.class, repository::findAll);
    }

    @Test
    void constructor_WithNullPath_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> new TestFileRepository(null));
    }

    @Test
    void constructor_WithEmptyPath_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> new TestFileRepository(""));
    }
}