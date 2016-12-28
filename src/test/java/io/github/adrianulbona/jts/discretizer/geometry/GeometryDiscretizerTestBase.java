package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import io.github.adrianulbona.jts.discretizer.util.WGS84Point2Coordinate;
import org.junit.jupiter.api.BeforeEach;

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
		return this.geometryFactory.createLineString(new Coordinate[]{p1(), p2(), p3(), p4(), p5(), p6()});
	}

	protected Set<GeoHash> discretized123456() {
		return Stream.of("u2rf", "u2rg", "u82d", "u2rm", "u82f", "u2rk", "u2rp", "u2rq", "u2qp",
				"u2rn", "u2qn",
				"u2w8", "u2rs", "u2qr", "u2qx", "u824", "u2mw", "u826", "u2wb", "u2qz", "u2my", "u2re")
				.map(GeoHash::fromGeohashString).collect(toSet());
	}

	protected LineString lineString1234() {
		return this.geometryFactory.createLineString(new Coordinate[]{p1(), p2(), p3(), p4()});
	}

	protected LineString lineString456() {
		return this.geometryFactory.createLineString(new Coordinate[]{p4(), p5(), p6()});
	}

	private Coordinate p1() {
		return point(new WGS84Point(46.760796734739515, 23.62060546875));
	}

	private Coordinate p2() {
		return point(new WGS84Point(47.0160778535083, 21.8408203125));
	}

	private Coordinate p3() {
		return point(new WGS84Point(47.537833024429865, 21.59912109375));
	}

	private Coordinate p4() {
		return point(new WGS84Point(47.89263630713117, 20.89599609375));
	}

	private Coordinate p5() {
		return point(new WGS84Point(47.7303180218142, 20.19287109375));
	}

	private Coordinate p6() {
		return point(new WGS84Point(47.49331270499546, 19.0283203125));
	}

	private Coordinate point(WGS84Point wgs84Point) {
		return this.wgs84Point2Coordinate.apply(wgs84Point);
	}
}
