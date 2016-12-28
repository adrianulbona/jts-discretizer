package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTWriter;
import io.github.adrianulbona.jts.discretizer.util.Coordinate2WGS84Point;
import io.github.adrianulbona.jts.discretizer.util.GeoHash2Geometry;
import io.github.adrianulbona.jts.discretizer.util.SegmentDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.WGS84Point2Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by adrianulbona on 28/12/2016.
 */
class LineStringDiscretizerTest {

	private WGS84Point2Coordinate wgs84Point2Coordinate;
	private LineStringDiscretizer discretizer;
	private GeometryFactory geometryFactory;

	@BeforeEach
	void setUp() {
		final Coordinate2WGS84Point coordinate2WGS84Point = new Coordinate2WGS84Point();
		this.wgs84Point2Coordinate = new WGS84Point2Coordinate();
		this.geometryFactory = new GeometryFactory();
		final CoordinateDiscretizer coordinateDiscretizer = new CoordinateDiscretizer(coordinate2WGS84Point, 4);
		final GeoHash2Geometry geoHash2Geometry = new GeoHash2Geometry(this.wgs84Point2Coordinate,
				this.geometryFactory);
		final SegmentDiscretizer segmentDiscretizer = new SegmentDiscretizer(coordinateDiscretizer, geoHash2Geometry,
				this.geometryFactory);
		this.discretizer = new LineStringDiscretizer(segmentDiscretizer);
	}

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.discretize(null));
	}

	@Test
	void discretizeEmptyLineString() {
		final LineString lineString = this.geometryFactory.createLineString(new Coordinate[]{});
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.discretize(lineString));
	}

	@Test
	void discretize() throws ParseException {
		final Coordinate p1 = this.wgs84Point2Coordinate.apply(new WGS84Point(46.760796734739515, 23.62060546875));
		final Coordinate p2 = this.wgs84Point2Coordinate.apply(new WGS84Point(47.0160778535083, 21.8408203125));
		final Coordinate p3 = this.wgs84Point2Coordinate.apply(new WGS84Point(47.537833024429865, 21.59912109375));
		final Coordinate p4 = this.wgs84Point2Coordinate.apply(new WGS84Point(47.89263630713117, 20.89599609375));
		final Coordinate p5 = this.wgs84Point2Coordinate.apply(new WGS84Point(47.7303180218142, 20.19287109375));
		final Coordinate p6 = this.wgs84Point2Coordinate.apply(new WGS84Point(47.49331270499546, 19.0283203125));
		final LineString lineString = this.geometryFactory.createLineString(new Coordinate[]{p1, p2, p3, p4, p5, p6});
		final Set<GeoHash> geoHashes = this.discretizer.discretize(lineString);
		//printDebugWKT(lineString, geoHashes);

		final Set<GeoHash> expected = Stream.of("u2rf", "u2rg", "u82d", "u2rm", "u82f", "u2rk", "u2rp", "u2rq", "u2qp",
				"u2rn", "u2qn",
				"u2w8", "u2rs", "u2qr", "u2qx", "u824", "u2mw", "u826", "u2wb", "u2qz", "u2my", "u2re")
				.map(GeoHash::fromGeohashString).collect(toSet());

		assertEquals(expected, geoHashes);
	}

	private void printDebugWKT(LineString lineString, Set<GeoHash> geoHashes) {
		final Set<Polygon> geometries = geoHashes
				.stream()
				.map(geoHash -> (Polygon) new GeoHash2Geometry(this.wgs84Point2Coordinate, this.geometryFactory).apply(
						geoHash))
				.collect(toSet());
		System.out.println(geoHashes.stream()
				.map(GeoHash::toBase32)
				.map(hash -> "\"" + hash + "\"")
				.collect(toSet()));
		final Polygon[] polygons = geometries.toArray(new Polygon[geometries.size()]);
		final MultiPolygon multiPolygon = this.geometryFactory.createMultiPolygon(polygons);

		final WKTWriter wktWriter = new WKTWriter();
		System.out.println(wktWriter.write(lineString));
		System.out.println(wktWriter.write(multiPolygon));
	}
}