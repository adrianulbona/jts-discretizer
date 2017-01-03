package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.*;
import io.github.adrianulbona.jts.discretizer.util.WGS84Point2Coordinate;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by adrianulbona on 28/12/2016.
 */
public class GeometryDiscretizerTestBase {

	protected WGS84Point2Coordinate wgs84Point2Coordinate = new WGS84Point2Coordinate();
	protected GeometryFactory geometryFactory = new GeometryFactory();

	protected LineString lineString123456() {
		return this.geometryFactory.createLineString(new Coordinate[]{c1(), c2(), c3(), c4(), c5(), c6()});
	}

	protected LinearRing linearRing123456() {
		return this.geometryFactory.createLinearRing(new Coordinate[]{c1(), c2(), c3(), c4(), c5(), c6(), c1()});
	}

	protected MultiPoint multiPoint123456() {
		return this.geometryFactory.createMultiPoint(new Coordinate[]{c1(), c2(), c3(), c4(), c5(), c6(), c1()});
	}

	protected MultiLineString multiLine123456() {
		return this.geometryFactory.createMultiLineString(new LineString[]{lineString1234(), lineString456()});
	}

	protected GeometryCollection geometryCollection() {
		return this.geometryFactory.createGeometryCollection(new Geometry[]{lineString1234(), lineString456()});
	}

	protected Set<GeoHash> discretized123456() {
		return Stream.of("u2rf", "u2rg", "u82d", "u2rm", "u82f", "u2rk", "u2rp", "u2rq", "u2qp",
				"u2rn", "u2qn",
				"u2w8", "u2rs", "u2qr", "u2qx", "u824", "u2mw", "u826", "u2wb", "u2qz", "u2my", "u2re")
				.map(GeoHash::fromGeohashString)
				.collect(toSet());
	}

	protected LineString lineString1234() {
		return this.geometryFactory.createLineString(new Coordinate[]{c1(), c2(), c3(), c4()});
	}

	protected LineString lineString456() {
		return this.geometryFactory.createLineString(new Coordinate[]{c4(), c5(), c6()});
	}

	protected Point point(Coordinate coordinate) {
		return this.geometryFactory.createPoint(coordinate);
	}

	protected Coordinate c1() {
		return coordinate(new WGS84Point(46.760796734739515, 23.62060546875));
	}

	private Coordinate c2() {
		return coordinate(new WGS84Point(47.0160778535083, 21.8408203125));
	}

	private Coordinate c3() {
		return coordinate(new WGS84Point(47.537833024429865, 21.59912109375));
	}

	private Coordinate c4() {
		return coordinate(new WGS84Point(47.89263630713117, 20.89599609375));
	}

	private Coordinate c5() {
		return coordinate(new WGS84Point(47.7303180218142, 20.19287109375));
	}

	private Coordinate c6() {
		return coordinate(new WGS84Point(47.49331270499546, 19.0283203125));
	}

	private Coordinate coordinate(WGS84Point wgs84Point) {
		return this.wgs84Point2Coordinate.apply(wgs84Point);
	}
}
