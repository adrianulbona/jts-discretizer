package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by adrianulbona on 27/12/2016.
 */
class GeoHash2GeometryTest {

	private GeoHash2Geometry geoHash2Geometry;

	@BeforeEach
	void setUp() {
		this.geoHash2Geometry = new GeoHash2Geometry(new WGS84Point2Coordinate(), new GeometryFactory());
	}

	@Test
	void applyNull() {
		assertThrows(IllegalArgumentException.class, () -> this.geoHash2Geometry.apply(null));
	}

	@Test
	void apply() {
		final Geometry u33Geometry = this.geoHash2Geometry.apply(GeoHash.fromGeohashString("u33"));
		assertEquals(u33Geometry.getEnvelope(), u33Geometry);
	}
}