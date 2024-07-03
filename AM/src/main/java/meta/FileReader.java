package meta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {

    public static List<Point> read(String fileName) {
        List<String> lines;
        try (InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            //lines = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Wystąpił błąd wczytywania pliku + " + fileName + ".\n" + e.getMessage());
        }
        List<Point> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            int number = Integer.parseInt(lines.get(i).split(" ")[0]);
            int x = Integer.parseInt(lines.get(i).split(" ")[1]);
            int y = Integer.parseInt(lines.get(i).split(" ")[2]);
            result.add(new Point(x, y, number));
        }
        return result;
    }
}
