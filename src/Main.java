import math.Model;
import math.FindNormals;
import objreader.ObjReader;
import math.Vector3f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            // Чтение содержимого файла .obj
            String fileContent = Files.readString(Path.of("C:\\Users\\PDS\\IdeaProjects\\CG3\\src\\WrapHead.obj"));

            // Парсинг файла OBJ в модель
            Model model = ObjReader.read(fileContent);

            // Вычисление нормалей
            ArrayList<Vector3f> normals = FindNormals.findNormals(model);

            // Вывод нормалей
            System.out.println("Calculated Normals:");
            for (Vector3f normal : normals) {
                System.out.println(normal);
            }
        } catch (IOException e) {
            System.err.println("Failed to read the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
