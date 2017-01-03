package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import io.github.adrianulbona.jts.discretizer.util.Coordinate2WGS84Point;
import io.github.adrianulbona.jts.discretizer.util.CoordinateDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.WGS84Point2Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Created by adrianulbona on 26/12/2016.
 */
class CoordinateDiscretizerTest {

	private WGS84Point2Coordinate wgs84Point2Coordinate;
	private CoordinateDiscretizer discretizer;

	@BeforeEach
	void setup() {
		this.wgs84Point2Coordinate = new WGS84Point2Coordinate();
		this.discretizer = new CoordinateDiscretizer(new Coordinate2WGS84Point());
	}

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.apply(null, 1));
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.apply(mock(Coordinate.class), null));
	}

	@Test
	void discretize() {
		final WGS84Point origin = new WGS84Point(0.0, 0.0);
		final Coordinate coordinate = this.wgs84Point2Coordinate.apply(origin);
		assertEquals(GeoHash.fromGeohashString("s0000"), this.discretizer.apply(coordinate, 5));
	}
}