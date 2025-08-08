package com.next.intune.common.controller;

import com.next.intune.common.api.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello Controller", description = "간단한 테스트 API")
public class HelloController {

    @GetMapping("/hello")
    @Operation(summary = "Hello 메시지 반환", description = "간단한 인사말을 반환합니다.")
    public ApiResult<String> hello() {
        return ApiResult.success("Hello, Spring Boot with Swagger!");
    }
}
