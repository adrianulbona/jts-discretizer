package io.github.adrianulbona.jts.discretizer.impl;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public class LineStringDiscretizer extends GeometryDiscretizer<LineString> {

	private final CoordinateDiscretizer coordinateDiscretizer;

	@Override
	public Set<GeoHash> discretize(@NonNull LineString geometry) {
		final Coordinate[] coordinates = geometry.getCoordinates();
		if (coordinates == null) {
			throw new IllegalArgumentException();
		}
		if (coordinates.length == 0) {
			return emptySet();
		}

		final GeoHash destination = this.coordinateDiscretizer.discretize(coordinates[coordinates.length - 1]);
		final Set<GeoHash> accumulator = new HashSet<>();

		Set<GeoHash> seed = Stream.of(this.coordinateDiscretizer.discretize(coordinates[0]))
				.collect(toSet());
		while (!accumulator.contains(destination)) {
			accumulator.addAll(seed);
			seed = seed.stream()
					.flatMap(this::seedCandidates)
					.distinct()
					.filter(geoHash -> !accumulator.contains(geoHash))
					.filter(geoHash -> geoHashGeometry(geoHash).intersects(geometry))
					.collect(toSet());
		}
		return accumulator;
	}

	private Geometry geoHashGeometry(GeoHash geoHash) {
		final BoundingBox boundingBox = geoHash.getBoundingBox();
		final Coordinate leftBottom = new Coordinate(boundingBox.getMinLat(), boundingBox.getMinLon());
		final Coordinate leftTop = new Coordinate(boundingBox.getMaxLat(), boundingBox.getMinLon());
		final Coordinate rightBottom = new Coordinate(boundingBox.getMinLat(), boundingBox.getMaxLon());
		final Coordinate rightTop = new Coordinate(boundingBox.getMaxLat(), boundingBox.getMaxLon());
		return new GeometryFactory().createPolygon(
				new Coordinate[]{leftBottom, leftTop, rightTop, rightBottom, leftBottom});
	}

	private Stream<GeoHash> seedCandidates(GeoHash geoHash) {
		return Stream.of(geoHash.getEasternNeighbour(), geoHash.getSouthernNeighbour(), geoHash.getWesternNeighbour(),
				geoHash.getNorthernNeighbour());
	}
}
