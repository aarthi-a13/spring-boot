package com.aarthi.contentcalendar.repository;

import com.aarthi.contentcalendar.model.Content;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends ListCrudRepository<Content, Integer> {
    List<Content> findAllByTitleContains(String type);

    //Custom query
    @Query("""
                SELECT * from Content where status = :status
            """)
    List<Content> listByStatus(@Param("status") String status);
}
