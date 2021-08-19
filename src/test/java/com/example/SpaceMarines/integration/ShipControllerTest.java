package com.example.SpaceMarines.integration;

import com.example.SpaceMarines.entities.DropShip;
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
public class ShipControllerTest {
    @LocalServerPort
    private int port;
    private final String url = Constant.URL;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpEntity<DropShip> shipHttpEntity;
    private HttpEntity<DropShip> shipHttpEntity2;

    @BeforeEach
    public void setup() {
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        shipHttpEntity = new HttpEntity<>(new DropShip("Alpha", 5, 18));
        shipHttpEntity2 = new HttpEntity<>(new DropShip("Bravo", 4, 36));
    }

    @Test
    public void testHealthEndpoint() throws Exception{
        assertThat(this.testRestTemplate.getForObject(url + port + "/ship/test", String.class)).contains(Constant.OK);
    }

    @Test
    public void addShipTest() throws Exception{
        this.testRestTemplate.postForEntity(url + port + "/ship/add", shipHttpEntity, DropShip.class);
        assertThat(this.testRestTemplate.getForObject(url + port + "/ship/get/id/1", DropShip.class)).toString().contains(shipHttpEntity.toString());
    }

    @Test
    public void deleteShipTest() throws Exception{
        this.testRestTemplate.postForEntity(url + port + "/ship/add", shipHttpEntity, DropShip.class);
        this.testRestTemplate.delete(url + port + "/ship/delete/1");
        assertThat(this.testRestTemplate.getForObject(url + port + "/ship/get/id/1", String.class)).contains("null");
    }

    @Test
    public void getAllTest() throws Exception{
        this.testRestTemplate.postForEntity(url + port + "/ship/add", shipHttpEntity, DropShip.class);
        this.testRestTemplate.postForEntity(url + port + "/ship/add", shipHttpEntity2, DropShip.class);
        Collection collection = this.testRestTemplate.getForObject(url + port + "/ship/get/all", Collection.class);
        assertThat(collection.contains("Alpha"));
        assertThat(collection.contains("Bravo"));
        assertThat(collection.size() == 2);
    }
}
