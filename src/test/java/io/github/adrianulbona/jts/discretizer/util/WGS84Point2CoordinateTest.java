package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by adrianulbona on 28/12/2016.
 */
class WGS84Point2CoordinateTest {

	@Test
	void applyNull() {
		assertThrows(IllegalArgumentException.class, () -> new WGS84Point2Coordinate().apply(null));
	}

	@Test
	void apply() {
		final double longitude = 10.0;
		final double latitude = 20.0;
		final Coordinate coordinate = new WGS84Point2Coordinate().apply(new WGS84Point(latitude, longitude));
		assertEquals(longitude, coordinate.x);
		assertEquals(latitude, coordinate.y);
	}
}