package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import io.github.adrianulbona.jts.discretizer.DiscretizerFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Created by adrianulbona on 28/12/2016.
 */
class GeometryCollectionDiscretizerTest extends GeometryDiscretizerTestBase {

	private GeometryCollectionDiscretizer discretizer;

	@BeforeEach
	void setUp() {
		this.discretizer = new GeometryCollectionDiscretizer(new DiscretizerFactoryImpl());
	}

	@Test
	void discretizeNull() {
		assertThrows(IllegalArgumentException.class,
				() -> this.discretizer.apply(mock(GeometryCollection.class), null));
		assertThrows(IllegalArgumentException.class, () -> this.discretizer.apply(null, 2));

	}

	@Test
	void discretize() {
		final MultiLineString multiLineString = this.geometryFactory.createMultiLineString(
				new LineString[]{lineString1234(), lineString456()});
		final Set<GeoHash> actual = this.discretizer.apply(multiLineString, 4);
		assertEquals(discretized123456(), actual);
	}

}