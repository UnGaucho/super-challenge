package com.interview.demo.auditing;

import com.interview.demo.auditing.model.RequestLogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestLogEntry, Long> {
    Page<RequestLogEntry> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
