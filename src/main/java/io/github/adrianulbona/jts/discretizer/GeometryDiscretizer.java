package io.github.adrianulbona.jts.discretizer;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Geometry;

import java.util.Set;

/**
 * Created by adrianulbona on 26/12/2016.
 */
public abstract class GeometryDiscretizer<G extends Geometry> {

	public abstract Set<GeoHash> discretize(G geometry);
}
