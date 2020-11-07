package com.elastic.search;

import com.elastic.search.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ComElasticSearchApplicationTests {


    @Resource
    private IndexService indexService;


    @Test
    public void createIndex(){
        indexService.createIndex();
    }

    @Test
    public void deleteIndex(){
        indexService.deleteIndex();
    }

    @Test
    public void addDocument() {
        indexService.addDocument();
    }


    @Test
    public void  getDocument() {
        indexService.getDocument();
    }

    @Test
    public void  updateDocument() {
        indexService.updateDocument();
    }

    @Test
    public void  deleteDocument() {
        indexService.deleteDocument();
    }

    @Test
    public void  termQuery() {
        indexService.termQuery();
    }

    @Test
    public void  termsQuery() {
        indexService.termsQuery();
    }

    @Test
    public void  matchAllQuery() {
        indexService.matchAllQuery();
    }

    @Test
    public void matchPhraseQuery(){
        indexService.matchPhraseQuery();
    }

    @Test
    public void matchMultiQuery() {
        indexService.matchMultiQuery();
    }

    @Test
    public void fuzzyQuery() {
        indexService.fuzzyQuery();
    }

    @Test
    public void wildcardQuery() {
        indexService.wildcardQuery();
    }

    @Test
    public void aggregationCount() {
        indexService.aggregationCount();
    }

    @Test
    public void aggregationPercentiles() {
        indexService.aggregationPercentiles();
    }
}

