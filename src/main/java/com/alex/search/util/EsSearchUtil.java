package com.alex.search.util;

import com.alex.search.model.Demo;
import com.alibaba.fastjson.JSON;
import org.frameworkset.elasticsearch.ElasticSearchException;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class EsSearchUtil {

    /**
     * 创建索引
     *
     * @param xmlName    索引model的xml名称
     * @param indexName  要创建的索引名称
     * @param configName 配置文件demo.xml中创建索引的property name
     */
    public void createIndice(String xmlName, String indexName, String configName) {
        //创建加载配置文件的客户端工具，单实例多线程安全，第一次运行要预加载，有点慢
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(xmlName);
        try {
            //先删除mapping
            clientUtil.dropIndice(indexName);
        } catch (ElasticSearchException e) {
            e.printStackTrace();
        }
        //再创建mapping
        clientUtil.createIndiceMapping(indexName, configName);//索引表mapping dsl脚本名称，在esmapper/demo.xml中定义createDemoIndice
    }

    /**
     * 生成document
     *
     * @param indexName 索引名称
     * @param object    要生成索引的对象试题
     */
    public String addOrUpdateDocument(String indexName, Object object) {
        //创建创建/修改/获取/删除文档的客户端对象，单实例多线程安全
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        //添加或者修改文档,如果demoId已经存在做修改操作，否则做添加文档操作，返回处理结果
        String response = clientUtil.addDocument(indexName, indexName, object);
        return response;
    }

    /**
     * 批量生成或更新document
     *
     * @param indexName  索引名称
     * @param objectList 要生成document的对象list
     */
    public void bulkAddAndUpdateDoument(String indexName, String indexType, List<Demo> objectList) {
        //创建批量创建文档的客户端对象，单实例多线程安全
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        //批量添加或者修改文档，将两个对象添加到索引表demo中
        String response = clientUtil.addDocuments(indexName, indexType, objectList);
    }

    public List<?> search(String xmlName, String indexName, String configName, Map paramMap, Object object) {
        //创建加载配置文件的客户端工具，用来检索文档，单实例多线程安全
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(xmlName);
        //执行查询，demo为索引表，_search为检索操作action
        ESDatas<?> esDatas = clientUtil.searchList(indexName + "/_search", configName, paramMap, object.getClass());
        //获取结果对象列表，最多返回1000条记录
        List<?> demos = esDatas.getDatas();
        return demos;
    }

    /**
     * 根据id删除document
     *
     * @param indexName
     * @param indexType
     * @param id
     * @return
     */
    public String deletDocumentById(String indexName, String indexType, String id) {
        //创建创建/修改/获取/删除文档的客户端对象，单实例多线程安全
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        String s = clientUtil.deleteDocument(indexName, indexType, id);
        return s;
    }

    public String bulkDelateDocument(String indexName, String indexType, List<String> ids) {
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        String jsonString = JSON.toJSONString(ids);
        jsonString = jsonString.replace("[", "").replace("]", "");
        String s = clientUtil.deleteDocuments(indexName, indexType, "2", "3");
        return s;
    }
}
