package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static ch.hsr.geohash.GeoHash.fromGeohashString;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by adrianulbona on 04/01/2017.
 */
class GeoHash2ChildrenStreamTest {

	@Test
	void expandNull() {
		assertThrows(IllegalArgumentException.class, () -> new GeoHash2ChildrenStream().apply(null));
	}

	@Test
	void expandExpandGeoHashWithMaximumPrecision() {
		assertThrows(IllegalArgumentException.class,
				() -> new GeoHash2ChildrenStream().apply(fromGeohashString("bbbbbbbbbbbb")));
	}

	@Test
	void expandExpandRoot() {
		final Set<GeoHash> children = new GeoHash2ChildrenStream().apply(fromGeohashString(""))
				.collect(toSet());
		assertEquals(32, children.size());
		children.forEach(child -> assertEquals(1, child.getCharacterPrecision()));
	}

	@Test
	void expandExpand() {
		final Set<GeoHash> children = new GeoHash2ChildrenStream().apply(fromGeohashString("b"))
				.collect(toSet());
		assertEquals(32, children.size());
		children.forEach(child -> assertEquals(2, child.getCharacterPrecision()));
	}
}