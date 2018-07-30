package com.gofluent.elasticsearch.controller;

import com.gofluent.elasticsearch.model.SearchObject;
import com.gofluent.elasticsearch.service.SearchObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("/rest/")
public class SearchController {

    @Autowired
    private SearchObjectService searchObjectService;

    @GetMapping(value = "/search/{keyword}/{start}/{end}")
    public List<SearchObject> getByKeyword(@PathVariable final String keyword, @PathVariable int start,
                                           @PathVariable int end) throws UnsupportedEncodingException {
        System.out.println("FIND ALL BY KEYWORD WITH INT");
        System.out.println("START " + start + " END " + end);
        return searchObjectService.findByKeyword( URLDecoder.decode(keyword,"UTF-8"), new PageRequest(start, end)).getContent();
    }

    @GetMapping(value = "/search/{keyword}")
    public List<SearchObject> findAllByKeyword(@PathVariable final String keyword,
                                               Pageable pageable) throws UnsupportedEncodingException {
        System.out.println("KEYWORD = " + keyword);
        System.out.println(URLDecoder.decode(keyword,"UTF-8"));
        return searchObjectService.findByKeyword( URLDecoder.decode(keyword,"UTF-8"), pageable).getContent();
    }

    @GetMapping(value = "/searchtype/{type}/{keyword}")
    public List<SearchObject> findByKeywordAndType(@PathVariable final String type, @PathVariable final String keyword,
                                                   Pageable pageable) throws UnsupportedEncodingException {
        System.out.println("KEYWORD = " + keyword);

        return searchObjectService.findByKeywordAndType( URLDecoder.decode(keyword,"UTF-8"), type, pageable).getContent();
    }

    @GetMapping(value = "/searchtype/{type}/{keyword}/{start}/{end}")
    public List<SearchObject> findByKeywordAndType(@PathVariable final String type, @PathVariable final String keyword,
                                           @PathVariable int start, @PathVariable int end) throws UnsupportedEncodingException {
        return searchObjectService.findByKeywordAndType( URLDecoder.decode(keyword,"UTF-8"), type, new PageRequest(start, end)).getContent();
    }
}
