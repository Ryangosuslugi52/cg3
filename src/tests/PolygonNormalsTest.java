package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import math.Vector3f;
import math.Model;
import math.Polygon;
import math.FindNormals;

class PolygonNormalsTest {
	private static ArrayList<Vector3f> temporaryNormals = new ArrayList<>();

	@BeforeAll
	static void init() {
		Model m = new Model();

		m.vertices.add(new Vector3f(-1, -1, 1));
		m.vertices.add(new Vector3f(-1, 1, 1));
		m.vertices.add(new Vector3f(-1, -1, -1));
		m.vertices.add(new Vector3f(-1, 1, -1));

		m.vertices.add(new Vector3f(1, -1, 1));
		m.vertices.add(new Vector3f(1, 1, 1));
		m.vertices.add(new Vector3f(1, -1, -1));
		m.vertices.add(new Vector3f(1, 1, -1));

		m.polygons.add(new Polygon(Arrays.asList(1, 2, 4, 3)));
		m.polygons.add(new Polygon(Arrays.asList(3, 4, 8, 7)));
		m.polygons.add(new Polygon(Arrays.asList(7, 8, 6, 5)));
		m.polygons.add(new Polygon(Arrays.asList(5, 6, 2, 1)));
		m.polygons.add(new Polygon(Arrays.asList(3, 7, 5, 1)));
		m.polygons.add(new Polygon(Arrays.asList(8, 4, 2, 6)));

		for (Polygon p : m.polygons) {
			temporaryNormals.add(FindNormals.findPolygonsNormals(m.vertices.get(p.getVertexIndices().get(0) - 1),
					m.vertices.get(p.getVertexIndices().get(1) - 1), m.vertices.get(p.getVertexIndices().get(2) - 1)));
		}

		for (int i = 0; i < m.vertices.size(); i++) {
			Set<Vector3f> polygonNormalsList = new HashSet<>();
			for (int j = 0; j < m.polygons.size(); j++) {
				if (m.polygons.get(j).getVertexIndices().contains(i)) {
					polygonNormalsList.add(temporaryNormals.get(j));
				}
			}
			m.normals.add(FindNormals.findVertexNormals(polygonNormalsList));
		}
	}

	@Test
	void polygonNormalsCubeTest0() {
		assertEquals(temporaryNormals.get(0).x, -1.0);
		assertEquals(temporaryNormals.get(0).y, 0.0);
		assertEquals(temporaryNormals.get(0).z, 0.0);
	}

	@Test
	void polygonNormalsCubeTest1() {
		assertEquals(temporaryNormals.get(1).x, 0.0);
		assertEquals(temporaryNormals.get(1).y, -0.0);
		assertEquals(temporaryNormals.get(1).z, -1.0);
	}

	@Test
	void polygonNormalsCubeTest2() {
		assertEquals(temporaryNormals.get(2).x, 1.0);
		assertEquals(temporaryNormals.get(2).y, 0.0);
		assertEquals(temporaryNormals.get(2).z, 0.0);
	}

	@Test
	void polygonNormalsCubeTest3() {
		assertEquals(temporaryNormals.get(3).x, 0.0);
		assertEquals(temporaryNormals.get(3).y, 0.0);
		assertEquals(temporaryNormals.get(3).z, 1.0);
	}

	@Test
	void polygonNormalsCubeTest4() {
		assertEquals(temporaryNormals.get(4).x, -0.0);
		assertEquals(temporaryNormals.get(4).y, -1.0);
		assertEquals(temporaryNormals.get(4).z, 0.0);
	}

	@Test
	void polygonNormalsCubeTest5() {
		assertEquals(temporaryNormals.get(5).x, -0.0);
		assertEquals(temporaryNormals.get(5).y, 1.0);
		assertEquals(temporaryNormals.get(5).z, 0.0);
	}

}
