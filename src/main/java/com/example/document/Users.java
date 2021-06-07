package com.example.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = Users.collectionName)  //库名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    public static final String collectionName = "user_info";

    @Id  //声明主键
    private Integer id;

    @Indexed //必要字段 创建索引
    private String name;
    private String teamName;
    private Long salary;

}
