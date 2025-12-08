package ma.emsi.ingestionvideo.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.ingestionvideo.dto.ApiResponse;
import ma.emsi.ingestionvideo.dto.CreateReporterRequest;
import ma.emsi.ingestionvideo.dto.ReporterResponse;
import ma.emsi.ingestionvideo.service.ReporterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reporter")
@RequiredArgsConstructor
public class ReporterController {

    final ReporterService service;

    @GetMapping("/test")
    public String test(){
        return "The ms is working!";
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReporterResponse>> createReporter(@RequestBody CreateReporterRequest request) {
        ReporterResponse reporter = service.createReporter(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Reporter created successfully", reporter));
    }

}
