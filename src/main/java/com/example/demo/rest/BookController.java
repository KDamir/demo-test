package com.example.demo.rest;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookSearchDto;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookFastRepository;
import com.example.demo.service.BookService;
import com.example.demo.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookFastRepository bookFastRepository;
    private final BookService bookService;

    public BookController(BookFastRepository bookFastRepository, BookService bookService) {
        this.bookFastRepository = bookFastRepository;
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<Book> create(@RequestBody BookDto dto) {
        logger.info("REST request to save Book");
        Book result = bookService.save(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> findAll(@RequestParam(value = "id", required = false) Long id,
                                                 @RequestParam(value = "bookName", required = false) String bookName,
                                                 @RequestParam(value = "desc", required = false) String desc,
                                                 Pageable pageable) {
        logger.info("REST request to get Books");
        BookSearchDto filter = new BookSearchDto();
        filter.id = id;
        filter.bookName = bookName;
        filter.description = desc;
        Page<BookDto> page = bookFastRepository.findAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/books");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
