package com.ps.quibbler.es.util;

import com.ps.quibbler.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class ElasticSearchUtils {

    @Value("${spring.elasticsearch.rest.uris}")
    private String uris;

    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    private void init() {
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
            if (StringUtils.isBlank(uris)) {
                log.error("elasticsearch.rest.uris is blank");
                return;
            }

            String[] uriArr = uris.split(",");
            HttpHost[] httpHostArr = new HttpHost[uriArr.length];
            int i = 0;
            for (String uri : uriArr) {
                if (StringUtils.isEmpty(uris)) {
                    continue;
                }

                try {
                    String[] split = uri.split(":");
                    String host = split[0];
                    String port = split[1];
                    HttpHost httpHost = new HttpHost(host, Integer.parseInt(port), "http");
                    httpHostArr[i++] = httpHost;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            RestClientBuilder builder = RestClient.builder(httpHostArr);
            restHighLevelClient = new RestHighLevelClient(builder);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 建立索引
     *
     * @param index
     * @return
     * @throws IOException
     */
    public boolean createIndex(String index) throws IOException {
        if (isIndexExist(index)) {
            log.error("Index is exists!");
            return false;
        }
        // 1.建立索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        // 2.执行客户端请求
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     * @throws IOException
     */
    public boolean deleteIndex(String index) throws IOException {
        if (!isIndexExist(index)) {
            log.error("Index is not exist!");
            return false;
        }
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     * @throws IOException
     */
    public boolean isIndexExist(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 创建/更新文档
     *
     * @param object
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public String createOrUpdateDocument(Object object, String index, String id) throws IOException {
        if (null == id) {
            return createDocument(object, index);
        }
        if (this.existsById(index, id)) {
            return this.updateDocumentByIdNoRealTime(object, index, id);
        } else {
            return createDocument(object, index, id);
        }
    }

    /**
     * 创建文档，自定义ID
     *
     * @param object
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public String createDocument(Object object, String index, String id) throws IOException{
        if (null == id) {
            return createDocument(object, index);
        }
        if (this.existsById(index, id)) {
            return this.updateDocumentByIdNoRealTime(object, index, id);
        }
        //建立请求
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(1));
        //将对象放入json
        request.source(JacksonUtil.toJSONString(object), XContentType.JSON);
        //客户端发送请求
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("Create Document success,index:{},response status:{},id:{}", index, response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 创建文档，随机ID
     *
     * @param object
     * @param index
     * @return
     * @throws IOException
     */
    public String createDocument(Object object,String index) throws IOException {
        return createDocument(object, index, UUID.randomUUID().toString().replace("-", "").toUpperCase());
    }
}
