package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by adrianulbona on 26/12/2016.
 */
class PointDiscretizerTest {

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class,
				() -> new PointDiscretizer(mock(CoordinateDiscretizer.class)).discretize(null));
	}

	@Test
	void discretize() {
		final Coordinate coordinate = mock(Coordinate.class);
		final Point point = mock(Point.class);
		when(point.getCoordinate()).thenReturn(coordinate);
		final CoordinateDiscretizer coordinateDiscretizer = mock(CoordinateDiscretizer.class);
		final GeoHash geoHash = GeoHash.fromGeohashString("u33");
		when(coordinateDiscretizer.discretize(coordinate)).thenReturn(geoHash);
		final Set<GeoHash> discretization = new PointDiscretizer(coordinateDiscretizer).discretize(point);
		assertEquals(1, discretization.size());
		assertEquals(Stream.of(geoHash)
				.collect(toSet()), discretization);
	}
}