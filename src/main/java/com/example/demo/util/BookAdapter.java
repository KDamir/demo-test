package com.example.demo.util;

import com.example.demo.dto.BookDto;

import java.util.Map;

public class BookAdapter {

    public static BookDto toDto(Map<String, Object> map) {
        ParamScanner scanner = new ParamScanner(map);
        BookDto dto = new BookDto();
        dto.id = scanner.getLong("id");
        dto.bookName = scanner.getString("book_name");
        dto.description = scanner.getString("description");
        return dto;
    }
}
