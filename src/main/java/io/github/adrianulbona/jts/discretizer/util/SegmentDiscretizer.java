package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import io.github.adrianulbona.jts.discretizer.geometry.CoordinateDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by adrianulbona on 27/12/2016.
 */
@RequiredArgsConstructor
public class SegmentDiscretizer implements BiFunction<Coordinate, Coordinate, Set<GeoHash>> {

	private final CoordinateDiscretizer coordinateDiscretizer;
	private final Function<GeoHash, Geometry> geoHash2Geometry;
	private final GeometryFactory geometryFactory;

	@Override
	public Set<GeoHash> apply(@NonNull Coordinate start, @NonNull Coordinate stop) {
		final LineString lineSegmentGeometry = this.geometryFactory.createLineString(new Coordinate[]{start, stop});
		final GeoHash destination = this.coordinateDiscretizer.discretize(stop);
		final Set<GeoHash> accumulator = new HashSet<>();
		Set<GeoHash> seed = Stream.of(this.coordinateDiscretizer.discretize(start))
				.collect(toSet());
		while (!accumulator.contains(destination)) {
			accumulator.addAll(seed);
			seed = seed.stream()
					.flatMap(geoHash -> Stream.of(geoHash.getAdjacent()))
					.distinct()
					.filter(geoHash -> this.geoHash2Geometry.apply(geoHash).intersects(lineSegmentGeometry))
					.collect(toSet());
		}
		return accumulator;
	}
}
