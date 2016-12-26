package io.github.adrianulbona.jts.discretizer.impl;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by adrianulbona on 26/12/2016.
 */
class PointDiscretizerTest {

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class, () -> new PointDiscretizer(5).discretize(null));
	}

	@Test
	void discretizeOutOfRangeCoords() {
		final GeometryFactory geometryFactory = new GeometryFactory();
		final PointDiscretizer pointDiscretizer = new PointDiscretizer(5);
		final Point latitudeTooSmall = geometryFactory.createPoint(new Coordinate(-91.0, 0.0));
		final Point latitudeTooLarge = geometryFactory.createPoint(new Coordinate(91.0, 0.0));
		final Point longitudeTooSmall = geometryFactory.createPoint(new Coordinate(0.0, -181.0));
		final Point longitudeTooLarge = geometryFactory.createPoint(new Coordinate(0.0, 181.0));

		assertThrows(IllegalArgumentException.class, () -> pointDiscretizer.discretize(latitudeTooSmall));
		assertThrows(IllegalArgumentException.class, () -> pointDiscretizer.discretize(latitudeTooLarge));
		assertThrows(IllegalArgumentException.class, () -> pointDiscretizer.discretize(longitudeTooSmall));
		assertThrows(IllegalArgumentException.class, () -> pointDiscretizer.discretize(longitudeTooLarge));
	}

	@Test
	void discretize() {
		final Point zerozero = new GeometryFactory().createPoint(new Coordinate(0.0, 0.0));
		final Set<GeoHash> geoHashes = new PointDiscretizer(5).discretize(zerozero);
		assertEquals(1, geoHashes.size());
		assertEquals(GeoHash.fromGeohashString("s0000"), geoHashes.stream()
				.findFirst()
				.orElseThrow(AssertionError::new));
	}
}