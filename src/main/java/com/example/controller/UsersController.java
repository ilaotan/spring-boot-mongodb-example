package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.document.Users;
import com.example.repository.UserRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/users")
public class UsersController {

    private UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<Users> getAll() {
        return userRepository.findAll();
    }


    @GetMapping("/getById")
    public Users getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/updateById")
    public String updateById(Integer id, String name) {

        Users user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setName(name);
            userRepository.save(user);
            return "ok";
        }
        return "不Ok";
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

        Users user = new Users();

        if(name != null) {
            user.setName(name);
        }

        // 其他字段自行书写
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains()) //模糊匹配
              .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.exact()) // 精确匹配
        ;

        //定义example条件对象
        Example<Users> example = Example.of(user,exampleMatcher);

//        PageRequest pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        PageRequest pageable = PageRequest.of(pageNum, pageSize);

        //分页查询
        Page<Users> all = userRepository.findAll(example, pageable);
        //总记录数
        long total = all.getTotalElements();
        //数据列表
        List<Users> content = all.getContent();

        //不要这么写 正规点.
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", content);

        return JSON.toJSONString(map);
    }


}
