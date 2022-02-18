
package com.example.demogw;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @SpringBootTest
class DemoGwApplicationTests {

	@Test
	void contextLoads() {
		ZonedDateTime zd = ZonedDateTime.now();
		log.info("{}", zd);
	}


}
