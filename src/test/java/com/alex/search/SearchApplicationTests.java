package com.alex.search;

import com.alex.search.model.Demo;
import com.alex.search.util.EsSearchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchApplicationTests {

    @Test
    public void contextLoads() {
        EsSearchUtil esSearchUtil = new EsSearchUtil();
        esSearchUtil.createIndice("esmapper/demo.xml", "demo", "createDemoIndice");
    }

    @Test
    public void bulkAddDocument() {
        EsSearchUtil esSearchUtil = new EsSearchUtil();
        List<Demo> demos = new ArrayList<Demo>();
        Demo demo = new Demo();//定义第一个对象
        demo.setDemoId(2L);
        demo.setAgentStarttime(new Date());
        demo.setApplicationName("alex");
        demo.setContentbody("ceshi ");
        demo.setName("刘德华");
        demos.add(demo);//添加第一个对象到list中

        demo = new Demo();//定义第二个对象
        demo.setDemoId(3L);
        demo.setAgentStarttime(new Date());
        demo.setApplicationName("yan");
        demo.setContentbody("四大天王，这种文化很好，中华人民共和国");
        demo.setName("张学友");
        demos.add(demo);//添加第二个对象到list中
        esSearchUtil.bulkAddAndUpdateDoument("demo", "demo", demos);
    }

    @Test
    public void testSearch() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("applicationName1","alex");
//        params.put("applicationName2","yan");
        params.put("name", "刘德华");
        // params.put("applicationName2", "yan");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置时间范围,时间参数接受long值
        params.put("startTime", dateFormat.parse("2017-09-02 00:00:00").getTime());
        params.put("endTime", new Date().getTime());
        EsSearchUtil esSearchUtil = new EsSearchUtil();
        List<Demo> search = (List<Demo>) esSearchUtil.search("esmapper/demo.xml", "demo", "searchDatas", params, new Demo());
        System.out.println(search);
    }

    @Test
    public void delete() {
        EsSearchUtil esSearchUtil = new EsSearchUtil();
        List<String> ids = Arrays.asList(new String[]{"2", "3"});
        //esSearchUtil.deletDocumentById("demo", "demo", "2");
        esSearchUtil.bulkDelateDocument("demo", "demo", ids);
    }


}
