package com.czxy.es.dao;

import com.czxy.es.pojo.ESWayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName WayBillRepository
 * @Author 宋明明
 * @Date 2018/9/29 15:54
 * Version 1.0
 **/
public interface WayBillRepository extends ElasticsearchRepository<ESWayBill,Integer> {
}
