package com.ps.quibbler.es.util;

import com.ps.quibbler.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            return this.updateDocumentByIdRealTime(object, index, id);
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
    public String createDocument(Object object, String index, String id) throws IOException {
        if (null == id) {
            return createDocument(object, index);
        }
        if (this.existsById(index, id)) {
            return this.updateDocumentByIdRealTime(object, index, id);
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
    public String createDocument(Object object, String index) throws IOException {
        return createDocument(object, index, UUID.randomUUID().toString().replace("-", "").toUpperCase());
    }

    /**
     * 通过ID删除文档
     *
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public String deleteDocumentById(String index, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return deleteResponse.getId();
    }

    /**
     * 通过ID更新文档
     *
     * @param object
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public String updateDocumentById(Object object, String index, String id) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.timeout("1s");
        updateRequest.doc(JacksonUtil.toJSONString(object), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        log.info("Update Document success,index:{},id:{},updateResponseId:{}", index, id, updateResponse.getId());
        return updateResponse.getId();
    }

    /**
     * 通过ID实时更新文档
     *
     * @param object
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public String updateDocumentByIdRealTime(Object object, String index, String id) throws IOException {
        //更新请求
        UpdateRequest updateRequest = new UpdateRequest(index, id);

        //确保文档实时更新
        updateRequest.setRefreshPolicy("wait_for");

        updateRequest.timeout("1s");
        updateRequest.doc(JacksonUtil.toJSONString(object), XContentType.JSON);
        //执行更新请求
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        log.info("Update Document real time success,index:{},id:{},updateResponseId:{}", index, id, updateResponse.getId());
        return updateResponse.getId();
    }

    /**
     * 通过ID获取文档
     *
     * @param index
     * @param id
     * @param fields
     * @return
     * @throws IOException
     */
    public Map<String, Object> getDocumentById(String index, String id, String fields) throws IOException {
        GetRequest request = new GetRequest(index, id);
        if (StringUtils.isNotEmpty(fields)) {
            request.fetchSourceContext(new FetchSourceContext(true, fields.split(","), Strings.EMPTY_ARRAY));
        }
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

    /**
     * 通过ID判断文件是否存在
     *
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public boolean existsById(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        //不获取返回的_source的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return restHighLevelClient.exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量插入文档
     *
     * @param index
     * @param objects
     * @return false 表示成功
     */
    public boolean bulkPost(String index, List<?> objects) {
        BulkRequest bulkRequest = new BulkRequest();
        BulkResponse response = null;
        for (Object object : objects) {
            IndexRequest request = new IndexRequest(index);
            request.source(JacksonUtil.toJSONString(object), XContentType.JSON);
            bulkRequest.add(request);
        }
        try {
            response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null != response && response.hasFailures();
    }

    /**
     * 获取低水平客户端
     *
     * @return
     */
    public RestClient getLowLevelClient() {
        return restHighLevelClient.getLowLevelClient();
    }

    /**
     * 高亮结果集
     *
     * @param searchResponse
     * @param highlightField
     * @return
     */
    private List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {

        // 存放解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> high = hit.getHighlightFields();
            HighlightField title = high.get(highlightField);
            // 初始查询结果
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            // 解析高亮字段，并将原来的字段替换为高亮字段
            if (title != null) {
                Text[] texts = title.fragments();
                StringBuilder nTitle = new StringBuilder();
                for (Text text : texts) {
                    nTitle.append(text);
                }
                sourceAsMap.put(highlightField, nTitle.toString());
            }
            list.add(sourceAsMap);
        }
        return list;
    }

    public List<Map<String, Object>> searchListData(String index, SearchSourceBuilder query, String highlightField) throws IOException {

        SearchRequest request = new SearchRequest(index);

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(highlightField);
        // 关闭多个高亮
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        //设置高亮配置
        query.highlighter(highlightBuilder);

        request.source(query);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("totalHist:" + response.getHits().getTotalHits());
        if (response.status().getStatus() == 200) {
            //解析文档
            return setSearchResponse(response, highlightField);
        }
        return null;
    }
}
