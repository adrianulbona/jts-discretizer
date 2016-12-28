package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import io.github.adrianulbona.jts.discretizer.util.Coordinate2WGS84Point;
import io.github.adrianulbona.jts.discretizer.util.CoordinateDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.GeoHash2Geometry;
import io.github.adrianulbona.jts.discretizer.util.SegmentDiscretizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by adrianulbona on 28/12/2016.
 */
class MultiLineStringDiscretizerTest extends GeometryDiscretizerTestBase {

	private LineStringDiscretizer lineStringDiscretizer;
	private MultiLineStringDiscretizer discretizer;

	@BeforeEach
	void setUp() {
		final Coordinate2WGS84Point coordinate2WGS84Point = new Coordinate2WGS84Point();
		final CoordinateDiscretizer coordinateDiscretizer = new CoordinateDiscretizer(coordinate2WGS84Point, 4);
		final GeoHash2Geometry geoHash2Geometry = new GeoHash2Geometry(this.wgs84Point2Coordinate,
				this.geometryFactory);
		final SegmentDiscretizer segmentDiscretizer = new SegmentDiscretizer(coordinateDiscretizer, geoHash2Geometry,
				this.geometryFactory);
		this.lineStringDiscretizer = new LineStringDiscretizer(segmentDiscretizer);
		this.discretizer = new MultiLineStringDiscretizer(lineStringDiscretizer);
	}

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.discretize(null));
	}

	@Test
	void discretize() {
		final Set<GeoHash> expected = this.lineStringDiscretizer.discretize(lineString123456());
		final MultiLineString multiLineString = this.geometryFactory.createMultiLineString(
				new LineString[]{lineString1234(), lineString456()});
		final Set<GeoHash> actual = this.discretizer.discretize(multiLineString);
		assertEquals(expected, actual);
	}

}