package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static ch.hsr.geohash.GeoHash.fromGeohashString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by adrianulbona on 04/01/2017.
 */
class GeoHash2ChildrenTest {

	@Test
	void expandNull() {
		assertThrows(IllegalArgumentException.class, () -> new GeoHash2Children().children(null));
	}

	@Test
	void expandExpandGeoHashWithMaximumPrecision() {
		assertThrows(IllegalArgumentException.class,
				() -> new GeoHash2Children().children(fromGeohashString("bbbbbbbbbbbb")));
	}

	@Test
	void expandExpand() {
		final Set<GeoHash> children = new GeoHash2Children().children(fromGeohashString("b"));
		assertEquals(32, children.size());
		children.forEach(child -> assertEquals(2, child.getCharacterPrecision()));
	}
}