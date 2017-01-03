package io.github.adrianulbona.jts.discretizer;

import io.github.adrianulbona.jts.discretizer.geometry.GeometryDiscretizerTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by adrianulbona on 31/12/2016.
 */
class DiscretizerFactoryImplITest extends GeometryDiscretizerTestBase {

	@Test
	void pointDiscretizer() {
		assertNotNull(new DiscretizerFactoryImpl().pointDiscretizer());
	}

	@Test
	void lineStringDiscretizer() {
		assertNotNull(new DiscretizerFactoryImpl().lineStringDiscretizer());
	}

	@Test
	void geometryCollectionDiscretizer() {
		assertNotNull(new DiscretizerFactoryImpl().geometryCollectionDiscretizer());
	}

	@Test
	void discretizer() {
		assertNotNull(new DiscretizerFactoryImpl().discretizer(point(c1())));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(lineString123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(linearRing123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(multiPoint123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(multiLine123456()));
		assertNotNull(new DiscretizerFactoryImpl().discretizer(multiLine123456()));
	}
}