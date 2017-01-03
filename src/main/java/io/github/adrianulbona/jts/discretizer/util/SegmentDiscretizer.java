package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
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
public class SegmentDiscretizer implements BiFunction<LineString, Integer, Set<GeoHash>> {

	private final BiFunction<Coordinate, Integer, GeoHash> coordinateDiscretizer;
	private final BiFunction<GeoHash, GeometryFactory, Geometry> geoHash2Geometry;

	@Override
	public Set<GeoHash> apply(@NonNull LineString segment, @NonNull Integer precision) {
		if (segment.getNumPoints() != 2) {
			throw new IllegalArgumentException();
		}
		final GeoHash destination = this.coordinateDiscretizer.apply(segment.getCoordinateN(1), precision);
		final Set<GeoHash> accumulator = new HashSet<>();
		Set<GeoHash> seed = Stream.of(this.coordinateDiscretizer.apply(segment.getCoordinateN(0), precision))
				.collect(toSet());
		while (!accumulator.contains(destination)) {
			accumulator.addAll(seed);
			seed = seed.stream()
					.flatMap(geoHash -> Stream.of(geoHash.getAdjacent()))
					.distinct()
					.filter(geoHash -> this.geoHash2Geometry.apply(geoHash, segment.getFactory())
							.intersects(segment))
					.collect(toSet());
		}
		return accumulator;
	}
}
