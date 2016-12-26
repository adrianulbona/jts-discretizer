package io.github.adrianulbona.jts.discretizer.impl;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Point;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import lombok.NonNull;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by adrianulbona on 26/12/2016.
 */
public class PointDiscretizer extends GeometryDiscretizer<Point> {

	public PointDiscretizer(int precision) {
		super(precision);
	}

	@Override
	public Set<GeoHash> discretize(@NonNull Point geometry) {
		final double latitude = geometry.getX();
		final double longitude = geometry.getY();
		return Stream.of(GeoHash.withCharacterPrecision(latitude, longitude, precision()))
				.collect(toSet());
	}
}
