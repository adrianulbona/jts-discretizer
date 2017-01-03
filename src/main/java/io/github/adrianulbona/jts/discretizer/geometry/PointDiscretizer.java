package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.CoordinateDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class PointDiscretizer implements GeometryDiscretizer<Point> {

	private final BiFunction<Coordinate, Integer, GeoHash> coordinateDiscretizer;

	@Override
	public Set<GeoHash> apply(@NonNull Point geometry, @NonNull Integer precision) {
		return Stream.of(this.coordinateDiscretizer.apply(geometry.getCoordinate(), precision))
				.collect(toSet());
	}
}
