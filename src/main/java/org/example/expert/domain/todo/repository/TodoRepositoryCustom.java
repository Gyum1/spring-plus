package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Predicate;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TodoRepositoryCustom {
    Page<Todo> search(String weather, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
