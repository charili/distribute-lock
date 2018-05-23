package com.maidao.util.distributelock;

import com.maidao.util.distributelock.lock.SimpleDistributedLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DistributeLockApplication.class)
@Import(DistributeLockApplication.class)
public class DistributeLockApplicationTests {
    @Autowired
	private SimpleDistributedLock simple;

	@Test
	public void contextLoads() throws Exception {

		for (int i = 0; i < 10; i++) {
			try {
				simple.acquire();
				System.out.println("正在进行运算操作：" + System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				simple.release();
				System.out.println("=================\r\n");
			}
		}
	}

}
