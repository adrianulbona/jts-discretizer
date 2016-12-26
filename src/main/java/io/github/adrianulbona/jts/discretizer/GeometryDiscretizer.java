package io.github.adrianulbona.jts.discretizer;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Geometry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * Created by adrianulbona on 26/12/2016.
 */
@RequiredArgsConstructor
public abstract class GeometryDiscretizer<G extends Geometry> {

	@Getter
	protected final int precision;

	public abstract Set<GeoHash> discretize(G geometry);
}
