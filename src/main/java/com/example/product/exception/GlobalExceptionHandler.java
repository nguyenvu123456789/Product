package com.example.product.exception;

import com.example.product.dto.common.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        String message = resolveMessage(ex.getMessageKey(), ex.getArgs(), ex.getMessage());
        return build(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        String message = resolveMessage("error.validation.failed", null, "Validation failed");
        ApiResponse<Void> body = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), message, fieldErrors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());

        String message = resolveMessage(
                ex.getMessage(),
                null,
                ex.getMessage()
        );

        return build(HttpStatus.BAD_REQUEST, message);
    }

    // ---- 400 : path variable / query param sai kiểu dữ liệu (VD: id=abc thay vì số) ----
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "?";
        String message = resolveMessage("error.type.mismatch", new Object[]{ex.getName(), requiredType},
                "Invalid value for parameter '" + ex.getName() + "'");
        return build(HttpStatus.BAD_REQUEST, message);
    }

    // ---- 400 : body JSON không đọc được (sai định dạng, thiếu dấu ngoặc, ...) ----
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Malformed request body: {}", ex.getMessage());
        String message = resolveMessage("error.malformed.request", null, "Malformed JSON request");
        return build(HttpStatus.BAD_REQUEST, message);
    }

    // ---- 409 : vi phạm ràng buộc DB (unique, foreign key, ...) ----
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Data integrity violation", ex);
        String message = resolveMessage("error.data.integrity", null, "Data conflict, please check your input");
        return build(HttpStatus.CONFLICT, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {

        log.error("Unhandled exception", ex);
        String message = resolveMessage("error.internal", null, "An unexpected error occurred");
        return build(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileStorage(FileStorageException ex) {
        log.error("File storage error: {}", ex.getMessage());
        String message = resolveMessage(ex.getMessageKey(), ex.getArgs(), ex.getMessage());
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(org.springframework.web.multipart.MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxUploadSize(
            org.springframework.web.multipart.MaxUploadSizeExceededException ex) {
        String message = resolveMessage("error.file.too.large", null, "File size exceeds the allowed limit");
        return build(HttpStatus.PAYLOAD_TOO_LARGE, message);
    }

    private ResponseEntity<ApiResponse<Void>> build(HttpStatus status, String message) {
        ApiResponse<Void> body = ApiResponse.error(status.value(), message);
        return new ResponseEntity<>(body, status);
    }


    private String resolveMessage(String key, Object[] args, String defaultMessage) {
        if (key == null) {
            return defaultMessage;
        }
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return defaultMessage;
        }
    }
}