package store.repository;

import static store.common.constant.ErrorMessages.NULL_OR_EMPTY_ERROR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import store.common.exception.ResourceNotFoundException;

public abstract class FileRepository<T> {
    protected final String resourcePath;

    protected FileRepository(String resourcePath) {
        validatePath(resourcePath);
        this.resourcePath = resourcePath;
    }

    public abstract T findAll();

    protected List<String> readLines() {
        try (BufferedReader reader = new BufferedReader(createInputStreamReader())) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new ResourceNotFoundException(resourcePath);
        }
    }

    private void validatePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new ResourceNotFoundException(NULL_OR_EMPTY_ERROR.getMessage());
        }
    }

    private InputStreamReader createInputStreamReader() {
        InputStream resourceStream = getClass().getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            throw new ResourceNotFoundException(resourcePath);
        }
        return new InputStreamReader(resourceStream, StandardCharsets.UTF_8);
    }

    public void save(T dto) {
    }
}
