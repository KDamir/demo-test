package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(BookDto dto) {
        Book entity = new Book();
        entity.setBookName(dto.bookName);
        entity.setDescription(dto.description);
        return bookRepository.save(entity);
    }
}
