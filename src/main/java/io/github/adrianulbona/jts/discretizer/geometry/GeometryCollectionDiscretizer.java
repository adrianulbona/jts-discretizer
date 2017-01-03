package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import io.github.adrianulbona.jts.discretizer.DiscretizerFactory;
import io.github.adrianulbona.jts.discretizer.GeometryDiscretizer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

/**
 * Created by adrianulbona on 28/12/2016.
 */
@RequiredArgsConstructor
public class GeometryCollectionDiscretizer implements GeometryDiscretizer<GeometryCollection> {

	private final DiscretizerFactory discretizerFactory;

	@Override
	public Set<GeoHash> apply(@NonNull GeometryCollection geometryCollection, @NonNull Integer precision) {
		return range(0, geometryCollection.getNumGeometries())
				.mapToObj(index -> {
					final Geometry geometry = geometryCollection.getGeometryN(index);
					return this.discretizerFactory.discretizer(geometry).apply(geometry, precision);
				})
				.flatMap(Set::stream)
				.collect(toSet());
	}
}
