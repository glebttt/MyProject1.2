package code.model.data.loaders;

import code.model.objects.PlanetObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FileDataLoader implements DataLoader {
    @Override
    public void load(Map<String, PlanetObject> planetObjects) {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/initial_stats.xlsx")) {
            assert inputStream != null : "Initial stats resource not found";
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 1; index <= 11; index++) {
                    Row row = sheet.getRow(index);

                    String name = row.getCell(0).getStringCellValue();

                    double[] coordinates = new double[3];
                    for (int i = 0; i <= 2; i++)
                        coordinates[i] = row.getCell(i + 1).getNumericCellValue();

                    double[] velocity = new double[3];
                    for (int i = 0; i <= 2; i++)
                        velocity[i] = row.getCell(i + 4).getNumericCellValue();

                    planetObjects.put(name, new PlanetObject(coordinates, velocity));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
