package com.czxy.bos.dao;

import com.czxy.bos.domain.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @ClassName ItemRepository
 * @Author 宋明明
 * @Date 2018/9/28 16:08
 * Version 1.0
 **/
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    List<Item> findByTitleLike(String title);
}
