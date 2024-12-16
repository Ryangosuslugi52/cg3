package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import math.Vector3f;
import math.FindNormals;
import math.Model;
import math.Polygon;
import objreader.ObjReader;

class Tests {

    // ===============================================
    // Tests for normalization functionality
    // ===============================================
    @Test
    void normalizeTest1() {
        assertEquals(Math.round(FindNormals.normalize(new Vector3f(0.33f, 0.33f, 0.33f)).x * 100), 58);
    }

    @Test
    void normalizeTest2() {
        assertEquals(Math.round(FindNormals.normalize(new Vector3f(1, 1, 1)).x * 100), 58);
    }

    @Test
    void normalizeTest3() {
        assertEquals(Math.round(FindNormals.normalize(new Vector3f(2, 2, 2)).x * 100), 58);
    }

    @Test
    void normalizeTest4() {
        assertEquals(FindNormals.normalize(new Vector3f(0, 0, 0)).x, 0);
    }

    @Test
    void normalizeTest5() {
        assertEquals(FindNormals.normalize(null), null);
    }

    // ===============================================
    // Tests for determinant functionality
    // ===============================================
    @Test
    void determinantTest1() {
        assertEquals(FindNormals.determinant(new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 1)), 1.0);
    }

    @Test
    void determinantTest2() {
        assertEquals(FindNormals.determinant(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)), 0.0);
    }

    @Test
    void determinantTest3() {
        assertEquals(FindNormals.determinant(new Vector3f(1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)), 0.0);
    }

    @Test
    void determinantTest4() {
        Vector3f a = new Vector3f(1, 0, 0);
        Vector3f b = new Vector3f(0, -1, 0);
        assertEquals(FindNormals.determinant(a, b, FindNormals.vectorProduct(a, b)), 1.0);
        assertEquals(FindNormals.determinant(b, a, FindNormals.vectorProduct(a, b)), -1.0);
    }

    // ===============================================
    // Tests for vector product functionality
    // ===============================================
    @Test
    void vectorProductTest1() {
        assertEquals(FindNormals.vectorProduct(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)).x, 0.0);
        assertEquals(FindNormals.vectorProduct(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)).y, 0.0);
        assertEquals(FindNormals.vectorProduct(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)).z, 0.0);
    }

    @Test
    void vectorProductTest2() {
        assertEquals(FindNormals.vectorProduct(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0)).x, 0.0);
        assertEquals(FindNormals.vectorProduct(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0)).y, 0.0);
        assertEquals(FindNormals.vectorProduct(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0)).z, 0.0);
    }

    @Test
    void vectorProductTest3() {
        assertEquals(FindNormals.vectorProduct(new Vector3f(1, 21, 17), new Vector3f(3, 0, -8)).x, -168.0);
        assertEquals(FindNormals.vectorProduct(new Vector3f(1, 21, 17), new Vector3f(3, 0, -8)).y, 59.0);
        assertEquals(FindNormals.vectorProduct(new Vector3f(1, 21, 17), new Vector3f(3, 0, -8)).z, -63.0);
    }

    // ===============================================
    // Tests for polygon normals (Cube example)
    // ===============================================
    private static ArrayList<Vector3f> temporaryNormals = new ArrayList<>();

    @BeforeAll
    static void initPolygonNormals() {
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
            temporaryNormals.add(FindNormals.findPolygonsNormals(
                    m.vertices.get(p.getVertexIndices().get(0) - 1),
                    m.vertices.get(p.getVertexIndices().get(1) - 1),
                    m.vertices.get(p.getVertexIndices().get(2) - 1)
            ));
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

    // ===============================================
    // Tests for vertex normals (Cube example)
    // ===============================================
    private static Model vertexNormalModel = ObjReader.read("# Blender 4.0.1\r\n" +
            "# www.blender.org\r\n" +
            "o Cube\r\n" +
            "v -1.000000 -1.000000 1.000000\r\n" +
            "v -1.000000 1.000000 1.000000\r\n" +
            "v -1.000000 -1.000000 -1.000000\r\n" +
            "v -1.000000 1.000000 -1.000000\r\n" +
            "v 1.000000 -1.000000 1.000000\r\n" +
            "v 1.000000 1.000000 1.000000\r\n" +
            "v 1.000000 -1.000000 -1.000000\r\n" +
            "v 1.000000 1.000000 -1.000000\r\n" +
            "vn -1.0000 -0.0000 -0.0000\r\n" +
            "vn -0.0000 -0.0000 -1.0000\r\n" +
            "vn 1.0000 -0.0000 -0.0000\r\n" +
            "vn -0.0000 -0.0000 1.0000\r\n" +
            "vn -0.0000 -1.0000 -0.0000\r\n" +
            "vn -0.0000 1.0000 -0.0000\r\n" +
            "s 0\r\n" +
            "f 1/1/1 2/2/1 4/3/1 3/4/1\r\n" +
            "f 3/5/2 4/6/2 8/7/2 7/8/2\r\n" +
            "f 7/9/3 8/10/3 6/11/3 5/12/3\r\n" +
            "f 5/13/4 6/14/4 2/15/4 1/16/4\r\n" +
            "f 3/17/5 7/18/5 5/19/5 1/20/5\r\n" +
            "f 8/21/6 4/22/6 2/23/6 6/24/6\r\n");

    @BeforeAll
    static void initVertexNormals() {
        vertexNormalModel.normals = FindNormals.findNormals(vertexNormalModel);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    void findVertexTestX(int i) {
        assertEquals(Math.abs(Math.round(vertexNormalModel.normals.get(i).x * 100)), 58);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    void findVertexTestY(int i) {
        assertEquals(Math.abs(Math.round(vertexNormalModel.normals.get(i).y * 100)), 58);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3 })
    void findVertexTestZ(int i) {
        assertEquals(Math.abs(Math.round(vertexNormalModel.normals.get(i).z * 100)), 58);
    }
}
