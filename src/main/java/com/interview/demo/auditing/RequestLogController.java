package com.interview.demo.auditing;

import com.interview.demo.auditing.model.RequestLogEntry;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class RequestLogController {

    private final RequestRepository requestLogRepository;

    public RequestLogController(RequestRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @GetMapping
    public ResponseEntity<Page<RequestLogEntry>> getAllLogs(
            @ParameterObject @SortDefault("createdAt") Pageable pageable) {
        Page<RequestLogEntry> logsPage = requestLogRepository.findAllByOrderByCreatedAtDesc(pageable);
        return new ResponseEntity<>(logsPage, HttpStatus.OK);
    }
}
