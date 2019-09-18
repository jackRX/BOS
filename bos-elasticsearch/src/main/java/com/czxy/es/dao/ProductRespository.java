package com.czxy.es.dao;

import com.czxy.es.pojo.EsPromotion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName ProductRespository
 * @Author 宋明明
 * @Date 2018/10/19 10:32
 * Version 1.0
 **/
public interface ProductRespository extends ElasticsearchRepository<EsPromotion,Integer> {
}
