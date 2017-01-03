package io.github.adrianulbona.jts.discretizer;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by adrianulbona on 30/12/2016.
 */
public interface DiscretizerFactory {

	<T extends Geometry> GeometryDiscretizer<T> discretizer(T geometry);

	GeometryDiscretizer<GeometryCollection> geometryCollectionDiscretizer();

	GeometryDiscretizer<LineString> lineStringDiscretizer();

	GeometryDiscretizer<Point> pointDiscretizer();
}
