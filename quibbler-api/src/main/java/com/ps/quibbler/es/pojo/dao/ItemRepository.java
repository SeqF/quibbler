package com.ps.quibbler.es.pojo.dao;

import com.ps.quibbler.es.pojo.po.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    List<Item> findByPriceBetween(double price1, double price2);
}
