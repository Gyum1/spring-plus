package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Todo> search(String weather, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        QTodo todo = QTodo.todo;

        BooleanBuilder builder = new BooleanBuilder();

        if (weather != null) {
            builder.and(todo.weather.eq(weather));
        }

        if (start != null && end != null) {
            builder.and(todo.modifiedAt.between(start, end));
        }

        List<Todo> content = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(builder)
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable,
                () -> queryFactory.selectFrom(todo).where(builder).fetch().size());
    }
}
