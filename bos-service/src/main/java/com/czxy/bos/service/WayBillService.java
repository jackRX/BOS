package com.czxy.bos.service;

import com.czxy.bos.dao.WayBillMapper;
import com.czxy.bos.take_delivery.WayBill;
import com.czxy.bos.vo.DataGridResult;
import com.czxy.es.dao.WayBillRepository;
import com.czxy.es.pojo.ESWayBill;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName WayBillService
 * @Author 宋明明
 * @Date 2018/9/25 10:47
 * Version 1.0
 **/
@Service
@Transactional
public class WayBillService {
    @Autowired
    private WayBillMapper wayBillMapper;

    @Autowired
    private WayBillRepository wayBillRepository;



    public DataGridResult findWayBillByPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        final List<WayBill> wayBills = wayBillMapper.selectAll();
        final PageInfo<WayBill> pageInfo = new PageInfo<>(wayBills);
        final DataGridResult gridResult = new DataGridResult();
        gridResult.setRows(pageInfo.getList());
        gridResult.setTotal(pageInfo.getTotal());
        return gridResult;
    }

    public void saveWayBill(WayBill wayBill){
        wayBillMapper.insert(wayBill);
    }


    public DataGridResult pageQuery(WayBill wayBill, Integer page, Integer rows) {
        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(wayBill.getWayBillNum())){
            final WildcardQueryBuilder wayBillNum = QueryBuilders.wildcardQuery("wayBillNum", "*" + wayBill.getWayBillNum() + "*");
            boolQuery.must(wayBillNum);
        }
        if (StringUtils.isNotBlank(wayBill.getSendAddress())){
            WildcardQueryBuilder sendAddressQueryBuilder = QueryBuilders.wildcardQuery("sendAddress", "*" + wayBill.getSendAddress() + "*");
            // 3 改进的第二个问题：北京顺义    这种方式要被允许-->这种方式需要对 查询词条进行分词后   再进行查询
            // QueryStringQueryBuilder此对象会对查询的词条分词后的各种情况进行分词查找
            // 参数：就是前台传过来的查询内容
//            QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress());
//            queryStringQueryBuilder.field("sendAddress").defaultOperator(Operator.AND);
            QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress").defaultOperator(Operator.AND);

            //4  sendAddressQueryBuilder 和  queryStringQueryBuilder  这两个查询的关系是什么？---should --should
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().should(sendAddressQueryBuilder).should(queryStringQueryBuilder);

            // 5 加进最外面的booleanQuery
            boolQuery.must(boolQueryBuilder);
        }
        if (StringUtils.isNotBlank(wayBill.getRecAddress())){
            final WildcardQueryBuilder recAddress = QueryBuilders.wildcardQuery("recAddress", "*" + wayBill.getRecAddress() + "*");
            boolQuery.must(recAddress);
        }
        if (StringUtils.isNotBlank(wayBill.getSendProNum())){
            final TermQueryBuilder sendProNum = QueryBuilders.termQuery("sendProNum", wayBill.getSendProNum());
            boolQuery.must(sendProNum);
        }
        if (wayBill.getSignStatus()!=null&&wayBill.getSignStatus()!=0){
            final TermQueryBuilder signStatus = QueryBuilders.termQuery("wayBillType", wayBill.getSignStatus());
            boolQuery.must(signStatus);
        }

        builder.withPageable(PageRequest.of(page-1,rows));
        builder.withQuery(boolQuery);

        final Page<ESWayBill> search = this.wayBillRepository.search(builder.build());
        final DataGridResult result = new DataGridResult();
        result.setTotal(search.getTotalElements());
        result.setRows(search.getContent());
        return result;
    }

    public List<WayBill> findAllWayBull() {
        return wayBillMapper.selectAll();
    }
}
