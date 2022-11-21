package com.yiming.blog;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void test1(){
        Page<Object> objectPage = new Page<>(2, 5);

        List<Object> records = objectPage.getRecords();
        System.out.println(records);
    }

}
