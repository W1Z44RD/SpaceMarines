package com.example.SpaceMarines.unit;

import com.example.SpaceMarines.controller.MarineController;
import com.example.SpaceMarines.controller.ShipController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class SpaceMarinesApplicationTests {
	@Autowired
	private MarineController marineController;
	@Autowired
	private ShipController shipController;

	@Test
	public void contextLoadsMarine() throws Exception {
		assertThat(marineController).isNotNull();

	}
	@Test
	public void contextLoadsShip() throws Exception {
		assertThat(shipController).isNotNull();

	}

}
