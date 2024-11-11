package store.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("유효한 경로로 파일을 조회하면 파일 내용을 반환한다")
    void findAll_ValidPath_ShouldReturnFileContents() {
        TestFileRepository repository = new TestFileRepository(VALID_RESOURCE_PATH);

        String result = repository.findAll();

        assertTrue(result.contains("test line 1"));
        assertTrue(result.contains("test line 2"));
    }

    @Test
    @DisplayName("유효하지 않은 경로로 파일을 조회하면 예외가 발생한다")
    void findAll_InvalidPath_ShouldThrowResourceNotFoundException() {
        TestFileRepository repository = new TestFileRepository(INVALID_RESOURCE_PATH);

        assertThrows(ResourceNotFoundException.class, repository::findAll);
    }

    @Test
    @DisplayName("null 경로로 리포지토리를 생성하면 예외가 발생한다")
    void constructor_WithNullPath_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> new TestFileRepository(null));
    }

    @Test
    @DisplayName("빈 경로로 리포지토리를 생성하면 예외가 발생한다")
    void constructor_WithEmptyPath_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> new TestFileRepository(""));
    }
}