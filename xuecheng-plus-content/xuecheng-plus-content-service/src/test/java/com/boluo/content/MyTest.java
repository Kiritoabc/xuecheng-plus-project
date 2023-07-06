package com.boluo.content;

import com.boluo.content.mapper.CourseBaseMapper;
import com.boluo.content.model.po.CourseBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MyTest.class)
class MyTest {

	@Autowired
	CourseBaseMapper courseBaseMapper;




	@Test
	void contextLoads() {

		CourseBase courseBase = courseBaseMapper.selectById(18);

		Assertions.assertNotNull(courseBase);
	}

}
