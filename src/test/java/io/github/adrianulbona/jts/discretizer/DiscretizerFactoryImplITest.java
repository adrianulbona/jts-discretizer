package io.github.adrianulbona.jts.discretizer;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import io.github.adrianulbona.jts.discretizer.geometry.GeometryDiscretizerTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by adrianulbona on 31/12/2016.
 */
class DiscretizerFactoryImplITest extends GeometryDiscretizerTestBase {

	@Test
	void discretizerNull() throws ParseException {
		assertThrows(IllegalArgumentException.class, () -> new DiscretizerFactoryImpl().discretizer(null));
	}

	@Test
	void discretizerUnknownGeometry() throws ParseException {
		final Geometry geometry = mock(Geometry.class);
		when(geometry.getGeometryType()).thenReturn("Hexagon");
		assertThrows(IllegalArgumentException.class, () -> new DiscretizerFactoryImpl().discretizer(geometry));
	}

	@Test
	void pointDiscretizer() {
		assertNotNull(new DiscretizerFactoryImpl().pointDiscretizer());
	}

	@Test
	void lineStringDiscretizer() {
		assertNotNull(new DiscretizerFactoryImpl().lineStringDiscretizer());
	}

	@Test
	void polygonDiscretizer() {
		assertNotNull(new DiscretizerFactoryImpl().polygonDiscretizer());
	}

	@Test
	void geometryCollectionDiscretizer() {
		assertNotNull(new DiscretizerFactoryImpl().geometryCollectionDiscretizer());
	}

	@Test
	void discretizer() throws ParseException {
		assertNotNull(new DiscretizerFactoryImpl().discretizer(point(c1())));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(lineString123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(linearRing123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(polygonCluj()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(multiPoint123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(multiLine123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(multiPolygonCluj()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(geometryCollection()));
	}
}