package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.MultiPoint;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.CoordinateDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class MultiPointDiscretizer extends GeometryDiscretizer<MultiPoint> {

	private final CoordinateDiscretizer coordinateDiscretizer;

	@Override
	public Set<GeoHash> discretize(@NonNull MultiPoint geometry) {
		return Stream.of(geometry.getCoordinates())
				.map(this.coordinateDiscretizer::discretize)
				.collect(toSet());
	}
}
