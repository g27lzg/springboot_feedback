package com.ydemo.base.app.service;

import com.ydemo.base.app.entity.Content;
import com.ydemo.base.app.iservice.ContentQueryService;
import com.ydemo.base.app.repository.ContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service(value="contentQueryService")
public class ContentQueryServiceImpl implements ContentQueryService {
    @Resource
    ContentRepository contentRepository;
    @Override
    public Page<Content> findContentNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        return contentRepository.findAll(pageable);
    }

    @Override
    public Page<Content> findContentCriteria(Integer page, Integer size, final Content contentQuery) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        Page<Content> contentPage = contentRepository.findAll(new Specification<Content>(){
            @Override
            public Predicate toPredicate(Root<Content> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null!=contentQuery.getTitle()&&!"".equals(contentQuery.getTitle())){
                    list.add(criteriaBuilder.equal(root.get("title").as(String.class), contentQuery.getTitle()));
                }
//                if(null!=contentQuery.getName()&&!"".equals(contentQuery.getName())){
//                    list.add(criteriaBuilder.equal(root.get("name").as(String.class), contentQuery.getName()));
//                }
//                if(null!=contentQuery.getIsbn()&&!"".equals(contentQuery.getIsbn())){
//                    list.add(criteriaBuilder.equal(root.get("isbn").as(String.class), contentQuery.getIsbn()));
//                }
//                if(null!=contentQuery.getAuthor()&&!"".equals(contentQuery.getAuthor())){
//                    list.add(criteriaBuilder.equal(root.get("author").as(String.class), contentQuery.getAuthor()));
//                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return contentPage;
    }
}