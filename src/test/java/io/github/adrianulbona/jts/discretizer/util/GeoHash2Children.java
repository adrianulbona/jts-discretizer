package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import lombok.NonNull;

import java.util.Set;

import static ch.hsr.geohash.GeoHash.fromLongValue;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.LongStream.range;

/**
 * Created by adrianulbona on 04/01/2017.
 */
public class GeoHash2Children {

	public Set<GeoHash> children(@NonNull GeoHash geoHash) {
		final int shift = 64 - geoHash.significantBits() - 5;
		final long base = geoHash.longValue() >> shift;
		return range(0, 32).mapToObj(index -> (base + index) << shift)
				.map(child -> fromLongValue(child, geoHash.significantBits() + 5))
				.collect(toSet());
	}
}
