package io.github.adrianulbona.jts.discretizer.impl;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by adrianulbona on 26/12/2016.
 */
class CoordinateDiscretizerTest {

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class, () -> new CoordinateDiscretizer(5).discretize(null));
	}

	@Test
	void discretizeOutOfRangeCoords() {
		final CoordinateDiscretizer discretizer = new CoordinateDiscretizer(5);
		final Coordinate latitudeTooSmall = new Coordinate(-91.0, 0.0);
		final Coordinate latitudeTooLarge = new Coordinate(91.0, 0.0);
		final Coordinate longitudeTooSmall = new Coordinate(0.0, -181.0);
		final Coordinate longitudeTooLarge = new Coordinate(0.0, 181.0);

		assertThrows(IllegalArgumentException.class, () -> discretizer.discretize(latitudeTooSmall));
		assertThrows(IllegalArgumentException.class, () -> discretizer.discretize(latitudeTooLarge));
		assertThrows(IllegalArgumentException.class, () -> discretizer.discretize(longitudeTooSmall));
		assertThrows(IllegalArgumentException.class, () -> discretizer.discretize(longitudeTooLarge));
	}

	@Test
	void discretize() {
		final Coordinate zerozero = new Coordinate(0.0, 0.0);
		final GeoHash geoHash = new CoordinateDiscretizer(5).discretize(zerozero);
		assertEquals(GeoHash.fromGeohashString("s0000"), geoHash);
	}
}