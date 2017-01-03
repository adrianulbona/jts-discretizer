package io.github.adrianulbona.jts.discretizer;

import ch.hsr.geohash.GeoHash;
import com.vividsolutions.jts.geom.Geometry;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Created by adrianulbona on 26/12/2016.
 */
public interface GeometryDiscretizer<G extends Geometry> extends BiFunction<G, Integer, Set<GeoHash>> {
}
