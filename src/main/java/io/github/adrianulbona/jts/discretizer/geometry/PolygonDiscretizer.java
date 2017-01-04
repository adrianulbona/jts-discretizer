package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static ch.hsr.geohash.GeoHash.fromGeohashString;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;

/**
 * Created by adrianulbona on 03/01/2017.
 */
@RequiredArgsConstructor
public class PolygonDiscretizer implements GeometryDiscretizer<Polygon> {

	private final Function<GeoHash, Stream<GeoHash>> geoHash2ChildrenStream;
	private final BiFunction<GeoHash, GeometryFactory, Geometry> geoHash2Geometry;

	@Override
	public Set<GeoHash> apply(@NonNull Polygon polygon, @NonNull Integer precision) {
		final Stream<GeoHash> seed = Stream.of(fromGeohashString(""));
		return intersectedGeoHashes(seed, precision, polygon).collect(toSet());
	}

	public Stream<GeoHash> intersectedGeoHashes(Stream<GeoHash> currentLevel, int levelsToExplore, Polygon polygon) {
		if (levelsToExplore == 0) {
			return currentLevel;
		}
		final Set<GeoHash> offspring = currentLevel
				.flatMap(this.geoHash2ChildrenStream)
				.filter(geoHash -> polygonIntersectsGeoHash(polygon, geoHash))
				.collect(toSet());

		final Set<GeoHash> covered = offspring.stream()
				.filter(geoHash -> polygonCoversGeoHash(polygon, geoHash)).collect(toSet());

		final Stream<GeoHash> newSeed = offspring.stream().filter(geoHash -> !covered.contains(geoHash));
		return concat(covered.stream(), intersectedGeoHashes(newSeed, levelsToExplore - 1, polygon));
	}

	private boolean polygonCoversGeoHash(Polygon polygon, GeoHash geoHash) {
		final Geometry geoHashGeometry = this.geoHash2Geometry.apply(geoHash, polygon.getFactory());
		return polygon.covers(geoHashGeometry);
	}

	private boolean polygonIntersectsGeoHash(Polygon polygon, GeoHash geoHash) {
		final Geometry geoHashGeometry = this.geoHash2Geometry.apply(geoHash, polygon.getFactory());
		return polygon.intersects(geoHashGeometry);
	}
}
