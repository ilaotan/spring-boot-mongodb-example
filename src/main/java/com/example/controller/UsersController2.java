package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.document.Users;
import com.example.repository.UserRepository;

/**
 *
 * MongoTemplate 用来书写复杂的查询.
 *
 */
@RestController
@RequestMapping("/rest/users2")
public class UsersController2 {


    @Autowired
    private MongoTemplate mongoTemplate;


    @GetMapping("/deleteById")
    public String deleteById(Integer id) {

        Query queryTemp = new Query();
        queryTemp.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(queryTemp, Users.class);

        return "ok";
    }


    @GetMapping("/findForPage")
    public String findForPage(Integer pageNum, Integer pageSize, @RequestParam(required = false) String name) {

        //分页查询对象
        if(pageNum<=0){
            pageNum = 1;
        }
        //注意mongo的分页 pageNum是从0开始的
        pageNum = pageNum-1;
        if(pageSize<=0){
            pageSize = 10;
        }



        Query query = new Query();
        Criteria criteria = new Criteria();

        if(name != null) {
//        criteria.andOperator(Criteria.where("name").is(walletName), Criteria.where("used").is(false));
            criteria.andOperator(Criteria.where("name").is(name));
        }

        query.addCriteria(criteria);

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        query.with(pageable);
//        query.with(Sort.by(Sort.Order.asc("tradeTime")));

        List<Users> items = mongoTemplate.find(query, Users.class);


        //不要这么写 正规点.
        Map<String, Object> map = new HashMap<>();
        map.put("total", items);

        return JSON.toJSONString(map);
    }




}
