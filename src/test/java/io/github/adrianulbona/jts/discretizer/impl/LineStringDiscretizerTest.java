package io.github.adrianulbona.jts.discretizer.impl;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static ch.hsr.geohash.GeoHash.fromGeohashString;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Created by adrianulbona on 27/12/2016.
 */
class LineStringDiscretizerTest {

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class,
				() -> new LineStringDiscretizer(mock(CoordinateDiscretizer.class)).discretize(null));
	}

	@Test
	void discretizeEmptyLineString() {
		assertEquals(Collections.emptySet(),
				new LineStringDiscretizer(mock(CoordinateDiscretizer.class)).discretize(
						new GeometryFactory().createLineString(new Coordinate[]{})));
	}

	@Test
	void discretize() {
		final Coordinate coordinate1 = new Coordinate(0.0, 0.0);
		final Coordinate coordinate2 = new Coordinate(45.0, 0.0);
		final LineString lineString = new GeometryFactory().createLineString(
				new Coordinate[]{coordinate1, coordinate2});
		final Set<GeoHash> discretization = new LineStringDiscretizer(
				new CoordinateDiscretizer(1)).discretize(lineString);
		assertEquals(4, discretization.size());
		final Set<GeoHash> expected = of(fromGeohashString("s"), fromGeohashString("k"), fromGeohashString("e"),
				fromGeohashString("u"))
				.collect(toSet());
		assertEquals(expected, discretization);
	}
}