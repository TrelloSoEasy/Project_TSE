package com.sparta.tse.common.testController;

import com.sparta.tse.common.entity.ApiResponse;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public ApiResponse<Null> test() {
        return ApiResponse.onSuccess(null);
    }
}
