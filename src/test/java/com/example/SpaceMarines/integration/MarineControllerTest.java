package com.example.SpaceMarines.integration;

import com.example.SpaceMarines.entities.Marine;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarineControllerTest {
    @LocalServerPort
    private int port;
    private final String url = Constant.URL;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpEntity<Marine> marineHttpEntity;
    private HttpEntity<Marine> marineHttpEntity2;

    @BeforeEach
    public void setup() {
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        marineHttpEntity = new HttpEntity<>(new Marine("Bob", "Private", 24));
        marineHttpEntity2 = new HttpEntity<>(new Marine("James", "Lieutenant", 35));
    }

    @Test
    public void testHealthEndpoint() throws Exception{
        assertThat(this.testRestTemplate.getForObject(url + port + "/marine/test", String.class)).contains(Constant.OK);
    }

    @Test
    public void addMarineTest() throws Exception{
        this.testRestTemplate.postForEntity(url + port + "/marine/add", marineHttpEntity, Marine.class);
        assertThat(this.testRestTemplate.getForObject(url + port + "/marine/get/id/1", Marine.class)).toString().contains(marineHttpEntity.toString());
    }

    @Test
    public void deleteMarineTest() throws Exception{
        this.testRestTemplate.postForEntity(url + port + "/marine/add", marineHttpEntity, Marine.class);
        this.testRestTemplate.delete(url + port + "/marine/delete/1");
        assertThat(this.testRestTemplate.getForObject(url + port + "/marine/get/id/1", String.class)).contains("null");
    }

    @Test
    public void patchMarineTest() throws Exception{
        this.testRestTemplate.postForEntity(url + port + "/marine/add", marineHttpEntity, Marine.class);
        assertThat(this.testRestTemplate.getForObject(url + port + "/marine/get/id/1", Marine.class)).toString().contains(marineHttpEntity.toString());
        HttpEntity<Marine> marineNewRank = new HttpEntity<>(new Marine("", "Lieutenant",0));
        this.testRestTemplate.patchForObject(url + port + "/marine/patch/rank/1", marineNewRank, Marine.class);
        assertThat(this.testRestTemplate.getForObject(url + port + "/marine/get/id/1", String.class)).contains("Lieutenant");
    }

    @Test
    public void getAllTest() throws Exception{
        this.testRestTemplate.postForEntity(url + port + "/marine/add", marineHttpEntity, Marine.class);
        this.testRestTemplate.postForEntity(url + port + "/marine/add", marineHttpEntity2, Marine.class);
        Collection collection = this.testRestTemplate.getForObject(url + port + "/marine/get/all", Collection.class);
        assertThat(collection.contains("Bob"));
        assertThat(collection.contains("James"));
        assertThat(collection.size() == 2);
    }
}
