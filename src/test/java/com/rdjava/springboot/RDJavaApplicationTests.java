package com.rdjava.springboot;

import com.rdjava.springboot.controller.ProductController;
import com.rdjava.springboot.dto.responses.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.filter.CorsFilter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RDJavaApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProductController productController;

	@Autowired
	private CorsFilter corsFilter;

	@Test
	void contextLoads() {
		assertThat(productController).isNotNull();
	}

	@Test
	public void corsFilterShouldBeConfigured() {
		assertThat(corsFilter).isNotNull();
	}

	@Test
	void findById_ShouldReturnProductResponse() {
		long productId = 1L;
		String url = "http://localhost:" + port + "/products/" + productId;
		ResponseEntity<ProductResponse> response = restTemplate.getForEntity(url, ProductResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo(productId);
		assertThat(response.getBody().getName()).isEqualTo(" Clean Code");
	}

	@Test
	void findById_ShouldReturnNotFound() {
		long productId = 999L;
		String url = "http://localhost:" + port + "/products/" + productId;
		ResponseEntity<ProductResponse> response = restTemplate.getForEntity(url, ProductResponse.class);
		assertThat(response.getBody()).isNull();
	}
}
