package com.czxy;

import com.czxy.bos.dao.ItemRepository;
import com.czxy.bos.domain.Item;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void CreatIndex() {
        template.createIndex(Item.class);
        template.putMapping(Item.class);
    }

    @Test
    public void deleteIndex(){
        template.deleteIndex(Item.class);
    }

    @Test
    public void Index(){
        Item item = new Item(1L, "小米手机7", " 手机","小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);
    }

    @Test
    public void SavaAllIndex(){
        List<Item> list=new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        itemRepository.saveAll(list);
    }

    @Test
    public void testQueryIndex(){
        Iterable<Item> list = this.itemRepository.findAll(Sort.by("price").ascending());
        for (Item item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void indexList(){
        final List<Item> list = itemRepository.findByTitleLike("手机7");
        for (Item item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void indexList1() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.baidu.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void serch(){
        final NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
         queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        final Page<Item> search = itemRepository.search(queryBuilder.build());
        final long totalElements = search.getTotalElements();
        System.out.println("总条数:"+totalElements);
        for (Item item : search) {
            System.out.println(item);
        }
    }

    @Test
    public void testMathQuery(){
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchQuery("title","坚果"));
        final Page<Item> search = itemRepository.search(builder.build());
        for (Item item : search) {
            System.out.println(item);
        }
    }

    @Test
    public void testTeamQuery(){
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("price","2799"));
        final Page<Item> search = itemRepository.search(builder.build());
        for (Item item : search) {
            System.out.println(item);
        }
    }

    @Test
    public void testBooleanQuery(){
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("title","小米")).must(QueryBuilders.termQuery("brand","小米")));
        final Page<Item> items = itemRepository.search(builder.build());
        for (Item item : items) {
            System.out.println(item);
        }

    }


    @Test
    public void seachByPage(){
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("category","手机"));
        int page=1;
        int size=2;
        builder.withPageable(PageRequest.of(page,size));

        final Page<Item> items = itemRepository.search(builder.build());
        final long totalElements = items.getTotalElements();
        System.out.println("总条数:"+totalElements);
        final int totalPages = items.getTotalPages();
        System.out.println("总页数:"+totalPages);
        final int number = items.getNumber();
        System.out.println("当前页:"+(number+1));
        final int size1 = items.getSize();
        System.out.println("每页数量:"+size1);

        for (Item item : items) {
            System.out.println(item);
        }
    }

    @Test
    public void seachAndSort(){
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("category","手机"));
        builder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        final Page<Item> search = this.itemRepository.search(builder.build());
        for (Item item : search) {
            System.out.println(item);
        }
    }

    @Test
    public void testAgg(){
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));
        builder.addAggregation(AggregationBuilders.terms("brands").field("brand"));
        final AggregatedPage<Item> search = (AggregatedPage<Item>)this.itemRepository.search(builder.build());
        final StringTerms brands =(StringTerms)search.getAggregation("brands");
        final List<StringTerms.Bucket> buckets = brands.getBuckets();
        for (StringTerms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        }
    }

    @Test
    public void testSubAgg(){
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));
        builder.addAggregation(AggregationBuilders.terms("brands").field("brand").subAggregation(AggregationBuilders.avg("priceAvg").field("price")));

        AggregatedPage<Item> aggpage= (AggregatedPage<Item>)this.itemRepository.search(builder.build());
        final StringTerms brands = (StringTerms)aggpage.getAggregation("brands");
        final List<StringTerms.Bucket> buckets = brands.getBuckets();
        for (StringTerms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString()+",共"+bucket.getDocCount()+"台");

            final InternalAvg priceAvg = (InternalAvg)bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价："+priceAvg.getValue());
        }
    }
}
