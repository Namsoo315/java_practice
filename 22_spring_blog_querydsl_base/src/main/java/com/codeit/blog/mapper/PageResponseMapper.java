package com.codeit.blog.mapper;

import com.codeit.blog.dto.base.SimplePageResponse;
import com.codeit.blog.dto.base.SlicePageResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface PageResponseMapper {

    // Page<Entity> → SimplePageResponse<Entity>
    default  <T> SimplePageResponse<T> toResponse(Page<T> page) {
        return SimplePageResponse.<T>builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .items(page.getContent())
                .build();
    }


    // Page<Entity> → SimplePageResponse<Dto>
    default  <T, R> SimplePageResponse<R> toResponse(Page<T> page, List<R> items) {
        return SimplePageResponse.<R>builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalItems(page.getTotalElements())
                .items(items)
                .build();
    }

    // Slice → SlicePageResponse
    default <T> SlicePageResponse<T> toSlice(Slice<T> slice) {
        String next = slice.hasNext() ? String.valueOf(slice.getNumber() + 1) : null;

        Instant nextAfter = null;
        if (slice.hasNext() && !slice.isEmpty()) {
            try {
                Object lastItem = slice.getContent().get(slice.getContent().size() - 1);
                var method = lastItem.getClass().getMethod("createdAt");
                Object result = method.invoke(lastItem);
                if (result instanceof Instant instant) {
                    nextAfter = instant;
                }
            } catch (Exception ignored) {
                // createdAt 없는 DTO인 경우 무시
            }
        }

        return SlicePageResponse.<T>builder()
                .size(slice.getSize())
                .hasNext(slice.hasNext())
                .nextCursor(next)
                .nextAfter(nextAfter)
                .totalElements(slice.getNumberOfElements())
                .content(slice.getContent())
                .build();
    }


    // Slice → SlicePageResponse
    default <T> SlicePageResponse<T> toSlice(Slice<T> slice, Object nextCursor) {
        return SlicePageResponse.<T>builder()
                .size(slice.getSize())
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor.toString())
                .totalElements(slice.getNumberOfElements())
                .content(slice.getContent())
                .build();
    }

    // Slice → SlicePageResponse
    default <T, R> SlicePageResponse<R> toSlice(Slice<T> slice, List<R> items) {
        String nextCursor = slice.hasNext() ? String.valueOf(slice.getNumber() + 1) : null;

        Instant nextAfter = null;
        if (slice.hasNext() && !items.isEmpty()) {
            Object lastItem = items.get(items.size() - 1);
            try {
                var createdAtField = lastItem.getClass().getDeclaredMethod("createdAt");
                nextAfter = (Instant) createdAtField.invoke(lastItem);
            } catch (Exception ignored) {
                // createdAt 없는 DTO인 경우 무시
            }
        }
        return SlicePageResponse.<R>builder()
                .size(slice.getSize())
                .hasNext(slice.hasNext())
                .nextCursor(Objects.toString(nextCursor, null))
                .nextAfter(nextAfter)
                .totalElements(slice.getNumberOfElements())
                .content(items)
                .build();
    }


}
