package io.github.adrianulbona.jts.discretizer.util;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPoint;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by adrianulbona on 27/12/2016.
 */
@RequiredArgsConstructor
public class GeoHash2Geometry implements BiFunction<GeoHash, GeometryFactory, Geometry> {

	private final Function<WGS84Point, Coordinate> WGS84Point2coordinate;

	@Override
	public Geometry apply(@NonNull GeoHash geoHash, @NonNull GeometryFactory geometryFactory) {
		final BoundingBox boundingBox = geoHash.getBoundingBox();
		final Coordinate upperLeft = this.WGS84Point2coordinate.apply(boundingBox.getUpperLeft());
		final Coordinate lowerRight = this.WGS84Point2coordinate.apply(boundingBox.getLowerRight());
		final MultiPoint multiPoint = geometryFactory.createMultiPoint(new Coordinate[]{upperLeft, lowerRight});
		return multiPoint.getEnvelope();
	}
}
