package com.microservice_ecommerce.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.cloud.config.server.environment.NativeEnvironmentProperties;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConfigserverApplicationTests {

	@Autowired
	private NativeEnvironmentProperties props;

	@Test
	void testNativeRepo() {
		System.out.println("Configured locations: " + Arrays.toString(props.getSearchLocations()));
		assertThat(props.getSearchLocations())
				.startsWith("file:///D:/Project/ecom-config-repo/");
	}

}
