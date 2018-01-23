package com.ydemo.base.app.iservice;

import com.ydemo.base.app.entity.Content;
import org.springframework.data.domain.Page;

public interface ContentQueryService {
    Page<Content> findContentNoCriteria(Integer page, Integer size);
    Page<Content> findContentCriteria(Integer page, Integer size, Content contentQuery);
}