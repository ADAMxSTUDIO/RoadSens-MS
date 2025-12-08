package ma.emsi.ingestionvideo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/*
 * The ApiResponse is a wrapper class that standardizes how the API responds to clients.
 * Instead of returning raw data, this class wraps it in a consistent structure.
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Success factory
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    // Error factory
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }
}