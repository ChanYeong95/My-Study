package pcy.study.api.common.api;

import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder
public record ApiResponse<T>(ApiResult result, @Valid T body) {

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(
                create(ApiResult.ok(), data)
        );
    }

    private static <T> ApiResponse<T> create(ApiResult result, T data) {
        return ApiResponse.<T>builder()
                .result(result)
                .body(data)
                .build();
    }

}
