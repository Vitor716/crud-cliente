package com.cliente.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiConfigurations.class)
class ApiConfigurationsTest {

	@Autowired
	private ApiConfigurations configurations;

	@Test
	void mapClienteSucesso() {
		Assertions.assertDoesNotThrow(() -> configurations.modeMapper());
	}
}
