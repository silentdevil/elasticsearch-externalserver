package com.gofluent.elasticsearch.repository;


import com.gofluent.elasticsearch.model.SearchObject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchObjectRepository extends ElasticsearchRepository<SearchObject, String> {}
