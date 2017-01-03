package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
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
		final CoordinateDiscretizer coordinateDiscretizer = new CoordinateDiscretizer(coordinate2WGS84Point);
		final GeoHash2Geometry geoHash2Geometry = new GeoHash2Geometry(wgs84Point2Coordinate);
		this.segmentDiscretizer = new SegmentDiscretizer(coordinateDiscretizer, geoHash2Geometry);
	}

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class, () -> this.segmentDiscretizer.apply(null, 1));
		assertThrows(IllegalArgumentException.class, () -> this.segmentDiscretizer.apply(mock(LineString.class), null));
	}

	@Test
	void discretizeInvalidLineString() {
		final LineString invalidLineString0 = new GeometryFactory().createLineString(new Coordinate[]{});
		assertThrows(IllegalArgumentException.class, () -> this.segmentDiscretizer.apply(invalidLineString0, 2));
		final LineString invalidLineString3 = new GeometryFactory().createLineString(
				new Coordinate[]{mock(Coordinate.class), mock(Coordinate.class), mock(Coordinate.class)});
		assertThrows(IllegalArgumentException.class, () -> this.segmentDiscretizer.apply(invalidLineString3, 2));
	}

	@Test
	void discretize() {
		final Coordinate coordinate1 = this.wgs84Point2Coordinate.apply(new WGS84Point(10.0, 10.0));
		final Coordinate coordinate2 = this.wgs84Point2Coordinate.apply(new WGS84Point(45.0, 10.0));
		final LineString lineString = new GeometryFactory().createLineString(
				new Coordinate[]{coordinate1, coordinate2});
		final Set<GeoHash> geoHashes = this.segmentDiscretizer.apply(lineString, 2);
		assertEquals(8, geoHashes.size());
		final Set<GeoHash> expected = of("s1", "s4", "s5", "sh", "sj", "sn", "sp", "u0").map(GeoHash::fromGeohashString)
				.collect(toSet());
		assertEquals(expected, geoHashes);
	}
}