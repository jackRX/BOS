package com.czxy.bos.service;

import com.czxy.bos.dao.PromotionMapper;
import com.czxy.bos.take_delivery.Promotion;
import com.czxy.bos.vo.DataGridResult;
import com.czxy.es.dao.ProductRespository;
import com.czxy.es.pojo.ESWayBill;
import com.czxy.es.pojo.EsPromotion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName PromotionService
 * @Author 宋明明
 * @Date 2018/9/20 09:32
 * Version 1.0
 **/
@Service
@Transactional
public class PromotionService {

    @Resource
    private PromotionMapper promotionMapper;

    @Resource
    private ProductRespository productRespository;

    public void savePromotion(Promotion promotion) {
        promotionMapper.insert(promotion);
    }

    public DataGridResult findPromotionByPage(Integer page, Integer rows,Promotion promotion) {

        if (promotion==null){
         // 使用分页助手开始分页,指定两个参数：当前页码，每页条数
        PageHelper.startPage(page, rows);
        // 然后分页拦截器会自动对接下来的查询进行分页
        List<Promotion> promotions = this.promotionMapper.select(null);// 不传查询条件
        // 封装分页信息对象
        PageInfo<Promotion> pageInfo = new PageInfo<>(promotions);
        // 封装页面数据对象
        DataGridResult result = new DataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
        }


        final NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(promotion.getTitle())){
            final WildcardQueryBuilder wayBillNum = QueryBuilders.wildcardQuery("title", "*" + promotion.getTitle() + "*");
            boolQuery.must(wayBillNum);
            builder.withQuery(boolQuery);
        }

        builder.withPageable(PageRequest.of(page-1,rows));
        builder.withSort(SortBuilders.fieldSort("endDate").order(SortOrder.ASC));
        final Page<EsPromotion> search = this.productRespository.search(builder.build());
        final DataGridResult result = new DataGridResult();
        result.setTotal(search.getTotalElements());
        result.setRows(search.getContent());

        return result;
    }
}
