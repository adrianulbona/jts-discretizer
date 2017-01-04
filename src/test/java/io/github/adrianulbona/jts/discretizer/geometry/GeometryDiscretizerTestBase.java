package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import io.github.adrianulbona.jts.discretizer.util.GeoHash2Geometry;
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

	protected Stream<String> discretizedPolygonCluj5() {
		return Stream.of("u82f3", "u82f2", "u82dp", "u82f1", "u82f0", "u82c8", "u82f6", "u82f5",
				"u82f4", "u829z", "u82c9", "u82cg", "u82cf", "u82cc", "u82cb");
	}

	protected Polygon polygonCluj() throws ParseException {
		return (Polygon) new WKTReader().read(
				"POLYGON((23.527908325195312 46.78519970334831,23.5272216796875 46.71886484380867,23.624725341796875 46.70615310945258,23.68927001953125 46.75698208294664,23.678970336914062 46.79648261213551,23.637771606445312 46.82843800916944,23.527908325195312 46.78519970334831))");
	}

	protected MultiPolygon multiPolygonCluj() throws ParseException {
		return this.geometryFactory.createMultiPolygon(new Polygon[]{polygonCluj()});
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

	protected void printDebugWKT(Set<GeoHash> geoHashes) {
		final Set<Polygon> geometries = geoHashes
				.stream()
				.map(geoHash -> (Polygon) new GeoHash2Geometry(new WGS84Point2Coordinate()).apply(
						geoHash, new GeometryFactory()))
				.collect(toSet());
		System.out.println(geoHashes.stream()
				.map(GeoHash::toBase32)
				.map(hash -> "\"" + hash + "\"")
				.collect(toSet()));
		final Polygon[] polygons = geometries.toArray(new Polygon[geometries.size()]);
		final MultiPolygon multiPolygon = new GeometryFactory().createMultiPolygon(polygons);

		final WKTWriter wktWriter = new WKTWriter();
		System.out.println(wktWriter.write(multiPolygon));
	}
}
