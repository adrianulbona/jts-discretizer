package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Point;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class PointDiscretizer extends GeometryDiscretizer<Point> {

	private final CoordinateDiscretizer coordinateDiscretizer;

	@Override
	public Set<GeoHash> discretize(@NonNull Point geometry) {
		return Stream.of(this.coordinateDiscretizer.discretize(geometry.getCoordinate()))
				.collect(toSet());
	}
}
