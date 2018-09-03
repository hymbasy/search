package com.alex.search.controller;

import com.alex.search.model.Demo;
import com.alex.search.util.EsSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private EsSearchUtil esSearchUtil;

    @RequestMapping("/query")
    public List<Demo> find(String name) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        // EsSearchUtil esSearchUtil = new EsSearchUtil();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置时间范围,时间参数接受long值
        params.put("startTime", dateFormat.parse("2017-09-02 00:00:00"));
        params.put("endTime", new Date());
        params.put("name", name);
        List<Demo> demos = (List<Demo>) esSearchUtil.search("esmapper/demo.xml", "demo", "searchDatas", params, new Demo());
        return demos;
    }

    @PostMapping("/add")
    public String add(Demo demo) {
        demo.setAgentStarttime(new Date());
        String msg = esSearchUtil.addOrUpdateDocument("demo", demo);
        return msg;
    }

    @PostMapping("/update")
    public String update(Demo demo) {
        demo.setAgentStarttime(new Date());
        String msg = esSearchUtil.addOrUpdateDocument("demo", demo);
        return msg;
    }
    
}
