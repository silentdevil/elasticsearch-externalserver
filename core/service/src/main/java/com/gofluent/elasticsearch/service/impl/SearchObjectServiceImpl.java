package com.gofluent.elasticsearch.service.impl;

import com.gofluent.elasticsearch.service.SearchObjectService;
import com.gofluent.elasticsearch.model.SearchObject;
import com.gofluent.elasticsearch.repository.SearchObjectRepository;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchObjectServiceImpl implements SearchObjectService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SearchObjectRepository searchObjectRepository;

    @Override
    public Page<SearchObject> findByKeyword(String keyword, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(buildQueryWithKeyword(keyword.replace("@"," ")))
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, SearchObject.class);
    }

    @Override
    public Page<SearchObject> findByKeywordAndType(String keyword, String type, Pageable pageable) {
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(buildQueryWithKeywordAndType(keyword.replace("@"," "), type))
                .build();
        return elasticsearchTemplate.queryForPage(build, SearchObject.class);
    }

    @Override
    public List<SearchObject> getAll() {
        List<SearchObject> searchObjects = new ArrayList<>();
        searchObjectRepository.findAll().forEach(searchObjects::add);
        return searchObjects;
    }

    private QueryBuilder buildQueryWithKeyword(String keyword) {
        return QueryBuilders.boolQuery().should(QueryBuilders.queryStringQuery(keyword)
                                                .fuzziness(Fuzziness.TWO)
                                                .lenient(true)
                                                .field("keyword"))
                                        .must(QueryBuilders.queryStringQuery("*" + keyword + "*")
                                                .lenient(true)
                                                .fuzziness(Fuzziness.TWO)
                                                .defaultOperator(QueryStringQueryBuilder.Operator.AND)
                                                .field("keyword"));
    }

    private QueryBuilder buildQueryWithKeywordAndType(String keyword, String type) {
        return QueryBuilders.boolQuery().should(QueryBuilders.queryStringQuery(keyword)
                                                .fuzziness(Fuzziness.ONE)
                                                .lenient(true)
                                                .field("keyword"))
                                        .must(QueryBuilders.queryStringQuery(type)
                                                                        .fuzziness(Fuzziness.ONE)
                                                                        .lenient(true)
                                                                        .field("type"))
                .must(QueryBuilders.queryStringQuery("*" + keyword + "*")
                        .lenient(true)
                        .fuzziness(Fuzziness.ONE)
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND)
                        .field("keyword"));
    }
}
