package io.github.adrianulbona.jts.discretizer.impl;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class CoordinateDiscretizer {

	private final int precision;

	public GeoHash discretize(@NonNull Coordinate coordinate) {
		return GeoHash.withCharacterPrecision(coordinate.x, coordinate.y, this.precision);
	}
}
