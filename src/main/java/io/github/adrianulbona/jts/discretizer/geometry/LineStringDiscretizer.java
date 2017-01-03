package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.SegmentDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class LineStringDiscretizer implements GeometryDiscretizer<LineString> {

	private final BiFunction<LineString, Integer, Set<GeoHash>> segmentDiscretizer;

	@Override
	public Set<GeoHash> apply(@NonNull LineString geometry, @NonNull Integer precision) {
		final Coordinate[] coordinates = geometry.getCoordinates();
		if (coordinates.length < 2) {
			throw new IllegalArgumentException();
		}

		return range(1, coordinates.length).mapToObj(
				index -> {
					final Coordinate start = coordinates[index - 1];
					final Coordinate end = coordinates[index];
					final LineString segment = geometry.getFactory().createLineString(new Coordinate[]{start, end});
					return this.segmentDiscretizer.apply(segment, precision);
				})
				.flatMap(Set::stream)
				.collect(toSet());
	}
}
