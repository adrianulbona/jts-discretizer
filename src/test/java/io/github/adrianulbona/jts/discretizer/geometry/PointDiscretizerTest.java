package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import io.github.adrianulbona.jts.discretizer.util.CoordinateDiscretizer;
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
				() -> new PointDiscretizer(mock(CoordinateDiscretizer.class)).apply(mock(Point.class), null));
		assertThrows(IllegalArgumentException.class,
				() -> new PointDiscretizer(mock(CoordinateDiscretizer.class)).apply(null, 2));
	}

	@Test
	void discretize() {
		final Coordinate coordinate = mock(Coordinate.class);
		final Point point = mock(Point.class);
		when(point.getCoordinate()).thenReturn(coordinate);
		final CoordinateDiscretizer coordinateDiscretizer = mock(CoordinateDiscretizer.class);
		final GeoHash geoHash = GeoHash.fromGeohashString("u33");
		when(coordinateDiscretizer.apply(coordinate, 3)).thenReturn(geoHash);
		final Set<GeoHash> geoHashes = new PointDiscretizer(coordinateDiscretizer).apply(point, 3);
		assertEquals(1, geoHashes.size());
		assertEquals(Stream.of(geoHash)
				.collect(toSet()), geoHashes);
	}
}