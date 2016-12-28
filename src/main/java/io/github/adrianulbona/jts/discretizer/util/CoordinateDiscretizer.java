package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class CoordinateDiscretizer {

	private final Function<Coordinate, WGS84Point> coordinate2WGS84Point;
	private final int precision;

	public GeoHash discretize(@NonNull Coordinate coordinate) {
		final WGS84Point point = this.coordinate2WGS84Point.apply(coordinate);
		return GeoHash.withCharacterPrecision(point.getLatitude(), point.getLongitude(), this.precision);
	}
}
