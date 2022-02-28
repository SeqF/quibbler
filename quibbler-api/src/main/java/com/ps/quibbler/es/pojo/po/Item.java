package com.ps.quibbler.es.pojo.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "item" ,createIndex = false)
public class Item {

    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(index = false,type = FieldType.Keyword)
    private String images;
}
