package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Created by adrianulbona on 27/12/2016.
 */
class SegmentDiscretizerTest {

	private WGS84Point2Coordinate wgs84Point2Coordinate;
	private SegmentDiscretizer segmentDiscretizer;

	@BeforeEach
	void setUp() {
		final Coordinate2WGS84Point coordinate2WGS84Point = new Coordinate2WGS84Point();
		this.wgs84Point2Coordinate = new WGS84Point2Coordinate();
		final GeometryFactory geometryFactory = new GeometryFactory();
		final CoordinateDiscretizer coordinateDiscretizer = new CoordinateDiscretizer(coordinate2WGS84Point, 2);
		final GeoHash2Geometry geoHash2Geometry = new GeoHash2Geometry(wgs84Point2Coordinate, geometryFactory);
		this.segmentDiscretizer = new SegmentDiscretizer(coordinateDiscretizer, geoHash2Geometry, geometryFactory);
	}

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class,
				() -> new SegmentDiscretizer(mock(CoordinateDiscretizer.class), mock(GeoHash2Geometry.class),
						mock(GeometryFactory.class)).apply(null, mock(Coordinate.class)));
		assertThrows(IllegalArgumentException.class,
				() -> new SegmentDiscretizer(mock(CoordinateDiscretizer.class), mock(GeoHash2Geometry.class),
						mock(GeometryFactory.class)).apply(mock(Coordinate.class), null));
		assertThrows(IllegalArgumentException.class,
				() -> new SegmentDiscretizer(mock(CoordinateDiscretizer.class), mock(GeoHash2Geometry.class),
						mock(GeometryFactory.class)).apply(null, null));

	}

	@Test
	void discretize() {
		final Coordinate coordinate1 = this.wgs84Point2Coordinate.apply(new WGS84Point(10.0, 10.0));
		final Coordinate coordinate2 = this.wgs84Point2Coordinate.apply(new WGS84Point(45.0, 10.0));
		final Set<GeoHash> geoHashes = this.segmentDiscretizer.apply(coordinate1, coordinate2);
		assertEquals(8, geoHashes.size());
		final Set<GeoHash> expected = of("s1", "s4", "s5", "sh", "sj", "sn", "sp", "u0").map(GeoHash::fromGeohashString)
				.collect(toSet());
		assertEquals(expected, geoHashes);
	}
}