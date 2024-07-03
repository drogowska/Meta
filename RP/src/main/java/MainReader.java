import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class MainReader {

    static Logger logger = LoggerFactory.getLogger(MainReader.class);

    public static void read(String fileName) {
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName))
                    .withCSVParser(csvParser)
                    .build();
            List<String[]> lines = reader.readAll();
            Main.tour = new Tour(lines.size());
            Main.depot = new Client(0,
                    Integer.parseInt(lines.get(0)[0].replaceAll(String.valueOf((char) 65279), "")),
                    Integer.parseInt(lines.get(0)[1].replaceAll(String.valueOf((char) 65279), "")),
                    Integer.parseInt(lines.get(0)[2].replaceAll(String.valueOf((char) 65279), "")),
                    Integer.parseInt(lines.get(0)[3].replaceAll(String.valueOf((char) 65279), "")),
                    Integer.parseInt(lines.get(0)[4].replaceAll(String.valueOf((char) 65279), "")));

            for(int i=1; i<lines.size(); i++) {
                Main.tour.add(new Client(i,
                        Double.parseDouble(lines.get(i)[0].replaceAll(String.valueOf((char) 65279), "")),
                        Double.parseDouble(lines.get(i)[1].replaceAll(String.valueOf((char) 65279), "")),
                        Double.parseDouble(lines.get(i)[2].replaceAll(String.valueOf((char) 65279), "")),
                        Double.parseDouble(lines.get(i)[3].replaceAll(String.valueOf((char) 65279), "")),
                        Double.parseDouble(lines.get(i)[4].replaceAll(String.valueOf((char) 65279), ""))));
            }
        } catch (CsvException | IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
