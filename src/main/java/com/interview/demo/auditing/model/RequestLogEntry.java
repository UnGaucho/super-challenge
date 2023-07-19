package com.interview.demo.auditing.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestLogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String method;
    private String params;
    @Nullable
    private String body;
    private Integer status;
    @Nullable
    @Column(length = 65535, columnDefinition = "TEXT")
    private String responseBody;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    public RequestLogEntry(String path, String method, String params, String body) {
        this.path = path;
        this.method = method;
        this.params = params;
        this.body = body;
    }


}
