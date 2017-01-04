package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import io.github.adrianulbona.jts.discretizer.util.GeoHash2ChildrenStream;
import io.github.adrianulbona.jts.discretizer.util.GeoHash2Geometry;
import io.github.adrianulbona.jts.discretizer.util.WGS84Point2Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Created by adrianulbona on 04/01/2017.
 */
class PolygonDiscretizerTest extends GeometryDiscretizerTestBase {

	private PolygonDiscretizer discretizer;

	@BeforeEach
	void setUp() {
		final GeoHash2ChildrenStream geoHash2ChildrenStream = new GeoHash2ChildrenStream();
		final GeoHash2Geometry geoHash2Geometry = new GeoHash2Geometry(new WGS84Point2Coordinate());
		this.discretizer = new PolygonDiscretizer(geoHash2ChildrenStream, geoHash2Geometry);
	}

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.apply(null, 2));
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.apply(mock(Polygon.class), null));
	}

	@Test
	void discretizeCluj() throws ParseException {
		final Polygon cluj = polygonCluj();
		final Set<GeoHash> geoHashes = this.discretizer.apply(cluj, 5);
		final Set<GeoHash> expected = discretizedPolygonCluj5()
				.map(GeoHash::fromGeohashString)
				.collect(toSet());
		assertEquals(expected, geoHashes);
	}
}