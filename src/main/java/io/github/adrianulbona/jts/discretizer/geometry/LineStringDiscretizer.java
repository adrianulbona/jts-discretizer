package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.SegmentDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class LineStringDiscretizer extends GeometryDiscretizer<LineString> {

	private final SegmentDiscretizer segmentDiscretizer;

	@Override
	public Set<GeoHash> discretize(@NonNull LineString geometry) {
		final Coordinate[] coordinates = geometry.getCoordinates();
		if (coordinates.length < 2) {
			throw new IllegalArgumentException();
		}

		return range(1, coordinates.length).mapToObj(
				index -> this.segmentDiscretizer.apply(coordinates[index - 1], coordinates[index]))
				.flatMap(Set::stream)
				.collect(toSet());
	}
}
