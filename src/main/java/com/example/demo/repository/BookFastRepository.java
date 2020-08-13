package com.example.demo.repository;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookSearchDto;
import com.example.demo.util.BookAdapter;
import com.example.demo.util.ParamMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookFastRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BookFastRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    interface Params {
        String LIMIT = "limit";
        String OFFSET = "offset";
        String BOOK_ID = "bookId";
        String BOOK_NAME = "bookName";
        String DESC = "description";
    }

    @Transactional(readOnly = true)
    public Page<BookDto> findAll(BookSearchDto filter, Pageable pageable) {
        ParamMap<String, Object> paramMap = new ParamMap<>();

        paramMap.put(Params.LIMIT, pageable.getPageSize());
        paramMap.put(Params.OFFSET, pageable.getOffset());
        paramMap.put(Params.BOOK_ID, filter.id);
        paramMap.put(Params.BOOK_NAME, filter.bookName);
        paramMap.put(Params.DESC, filter.description);

        StringBuilder commonSb = new StringBuilder(" from book where (1 = 1) ");

        if (paramMap.isNotNull(Params.BOOK_ID)) {
            commonSb.append(" and id = :bookId");
        }

        if (paramMap.isNotEmptyString(Params.BOOK_NAME)) {
            commonSb.append(" and book_name = :bookName");
        }

        if (paramMap.isNotEmptyString(Params.DESC)) {
            commonSb.append(" and description = :description");
        }

        String mainSb = "select id, book_name, description " + commonSb.toString()
                + " ORDER BY id DESC LIMIT :limit OFFSET :offset";

        List<BookDto> dtoList = namedParameterJdbcTemplate.queryForList(mainSb, paramMap).stream()
                .map(BookAdapter::toDto)
                .collect(Collectors.toList());

        Long total = namedParameterJdbcTemplate.queryForObject("SELECT count(id)" + commonSb, paramMap, Long.class);

        return new PageImpl<>(dtoList, pageable, total);

    }

}
