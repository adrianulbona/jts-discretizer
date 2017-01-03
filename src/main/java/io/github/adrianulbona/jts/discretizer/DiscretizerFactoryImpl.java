package io.github.adrianulbona.jts.discretizer;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import com.vividsolutions.jts.geom.*;
import io.github.adrianulbona.jts.discretizer.geometry.GeometryCollectionDiscretizer;
import io.github.adrianulbona.jts.discretizer.geometry.LineStringDiscretizer;
import io.github.adrianulbona.jts.discretizer.geometry.PointDiscretizer;
import io.github.adrianulbona.jts.discretizer.util.*;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by adrianulbona on 30/12/2016.
 */
public class DiscretizerFactoryImpl implements DiscretizerFactory {

	@Override
	public <T extends Geometry> GeometryDiscretizer<T> discretizer(T geometry) {
		switch (geometry.getGeometryType()) {
			case "Point":
				return (GeometryDiscretizer<T>) pointDiscretizer();
			case "LineString":
			case "LinearRing":
				return (GeometryDiscretizer<T>) lineStringDiscretizer();
			case "GeometryCollection":
			case "MultiPoint":
			case "MultiLineString":
				return (GeometryDiscretizer<T>) geometryCollectionDiscretizer();
			case "MultiPolygon":
			case "Polygon":
			default:
				throw new IllegalArgumentException();

		}
	}

	@Override
	public GeometryDiscretizer<GeometryCollection> geometryCollectionDiscretizer() {
		return new GeometryCollectionDiscretizer(this);
	}

	@Override
	public GeometryDiscretizer<LineString> lineStringDiscretizer() {
		return new LineStringDiscretizer(segmentDiscretizer());
	}

	@Override
	public GeometryDiscretizer<Point> pointDiscretizer() {
		return new PointDiscretizer(coordinateDiscretizer());
	}

	private BiFunction<LineString, Integer, Set<GeoHash>> segmentDiscretizer() {
		return new SegmentDiscretizer(coordinateDiscretizer(), geoHash2Geometry());
	}

	private BiFunction<GeoHash, GeometryFactory, Geometry> geoHash2Geometry() {
		return new GeoHash2Geometry(wgs84Point2Coordinate());
	}

	private BiFunction<Coordinate, Integer, GeoHash> coordinateDiscretizer() {
		return new CoordinateDiscretizer(coordinate2WGS84Point());
	}

	private Function<WGS84Point, Coordinate> wgs84Point2Coordinate() {
		return new WGS84Point2Coordinate();
	}

	private Function<Coordinate, WGS84Point> coordinate2WGS84Point() {
		return new Coordinate2WGS84Point();
	}
}
