package store.common.exception;

import static store.common.constant.ErrorMessages.RESOURCE_NOT_FOUND;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String path) {
        super(RESOURCE_NOT_FOUND + path);
    }
}
