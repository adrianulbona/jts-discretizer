package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.MultiPoint;
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
class MultiPointDiscretizerTest {

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class,
				() -> new MultiPointDiscretizer(mock(CoordinateDiscretizer.class)).discretize(null));
	}

	@Test
	void discretize() {
		final Coordinate coordinate1 = mock(Coordinate.class);
		final Coordinate coordinate2 = mock(Coordinate.class);
		final Coordinate coordinate3 = mock(Coordinate.class);
		final MultiPoint multiPoint = mock(MultiPoint.class);
		when(multiPoint.getCoordinates()).thenReturn(new Coordinate[]{coordinate1, coordinate2, coordinate3});
		final CoordinateDiscretizer coordinateDiscretizer = mock(CoordinateDiscretizer.class);
		final GeoHash geoHash1 = GeoHash.fromGeohashString("u31");
		final GeoHash geoHash2 = GeoHash.fromGeohashString("u32");
		final GeoHash geoHash3 = GeoHash.fromGeohashString("u33");
		when(coordinateDiscretizer.discretize(coordinate1)).thenReturn(geoHash1);
		when(coordinateDiscretizer.discretize(coordinate2)).thenReturn(geoHash2);
		when(coordinateDiscretizer.discretize(coordinate3)).thenReturn(geoHash3);

		final Set<GeoHash> discretization = new MultiPointDiscretizer(coordinateDiscretizer).discretize(multiPoint);
		assertEquals(3, discretization.size());
		assertEquals(Stream.of(geoHash1, geoHash2, geoHash3)
				.collect(toSet()), discretization);
	}
}