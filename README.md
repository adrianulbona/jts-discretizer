# jts-discretizer 

[![Build Status](https://travis-ci.org/adrianulbona/jts-discretizer.svg?branch=master)](https://travis-ci.org/adrianulbona/jts-discretizer)
[![Coverage Status](https://coveralls.io/repos/github/adrianulbona/jts-discretizer/badge.svg)](https://coveralls.io/github/adrianulbona/jts-discretizer)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.adrianulbona/jts-discretizer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.adrianulbona/jts-discretizer)

This is a tiny Java 8 library able to convert [JTS](https://en.wikipedia.org/wiki/JTS_Topology_Suite) geometries to a set of [GeoHashes](https://en.wikipedia.org/wiki/Geohash) with arbitrary precision (<12). 

A GeoHash-based Mallorca discretization looks like:
![alt tag](https://github.com/adrianulbona/jts-discretizer/raw/master/img/polygon.png)

The precision used is 7 - notice that the GeoHash set is reduced if all children of a certain GeoHash are present.

## Maven:
```xml
<dependency>
    <groupId>io.github.adrianulbona</groupId>
    <artifactId>jts-discretizer</artifactId>
    <version>0.1.0</version>
</dependency>
```
## Code sample:
```java
final Geometry geometry = ...
final DiscretizerFactory discretizerFactory = new DiscretizerFactoryImpl();
final GeometryDiscretizer<Geometry> discretizer = discretizerFactory.discretizer(geometry);
final Set<GeoHash> geoHashes = discretizer.apply(geometry, 7);
```

