package io.github.adrianulbona.jts.discretizer.geometry;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
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
public class MultiLineStringDiscretizer extends GeometryDiscretizer<MultiLineString> {

	private final LineStringDiscretizer lineStringDiscretizer;

	@Override
	public Set<GeoHash> discretize(@NonNull MultiLineString geometry) {
		return range(0, geometry.getNumGeometries())
				.mapToObj(index -> (LineString) geometry.getGeometryN(index))
				.map(this.lineStringDiscretizer::discretize)
				.flatMap(Set::stream)
				.collect(toSet());
	}
}
