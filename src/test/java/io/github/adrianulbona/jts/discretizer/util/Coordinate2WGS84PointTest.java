package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by adrianulbona on 28/12/2016.
 */
class Coordinate2WGS84PointTest {

	@Test
	void applyNull() {
		assertThrows(IllegalArgumentException.class, () -> new Coordinate2WGS84Point().apply(null));
	}

	@Test
	void applyInvalidPositiveLatitude() {
		assertThrows(IllegalArgumentException.class, () -> new Coordinate2WGS84Point().apply(new Coordinate(0.0, 91.0)));
	}

	@Test
	void applyInvalidNegativeLatitude() {
		assertThrows(IllegalArgumentException.class, () -> new Coordinate2WGS84Point().apply(new Coordinate(0.0, -91.0)));
	}

	@Test
	void applyInvalidPositiveLongitude() {
		assertThrows(IllegalArgumentException.class, () -> new Coordinate2WGS84Point().apply(new Coordinate(181.0, 0.0)));
	}

	@Test
	void applyInvalidNegativeLongitude() {
		assertThrows(IllegalArgumentException.class, () -> new Coordinate2WGS84Point().apply(new Coordinate(-181.0, 0.0)));
	}

	@Test
	void apply() {
		final double longitude = 10.0;
		final double latitude = 20.0;
		final WGS84Point point = new Coordinate2WGS84Point().apply(new Coordinate(longitude, latitude));
		assertEquals(longitude, point.getLongitude());
		assertEquals(latitude, point.getLatitude());
	}

}