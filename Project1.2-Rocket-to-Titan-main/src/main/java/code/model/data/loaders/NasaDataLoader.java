package code.model.data.loaders;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class NasaDataLoader implements DataLoader {
    @Override
    public void load(Map<String, PlanetObject> planetObjects) {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/nasa_planet_codes.xlsx")) {
            assert inputStream != null : "Initial stats resource not found";
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 0; index <= 10; index++) {
                    Row row = sheet.getRow(index);

                    String name = row.getCell(0).getStringCellValue();
                    int planetCode = (int) row.getCell(1).getNumericCellValue();

                    planetObjects.put(name, new PlanetObject(planetCode));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        String startDate = "2023-04-1", endDate = "2023-04-11";
        String daysLong = "10d";
        String urlLoc = "https://ssd.jpl.nasa.gov/api/horizons.api?format=text&COMMAND='";
        BufferedReader br;


        for (Map.Entry<String, PlanetObject> entry : planetObjects.entrySet()) {
            System.out.println("trying to connect with planet: " + entry.getKey());
            urlLoc = urlLoc + entry.getValue().getPlanetCode() + "&OBJ_DATA='NO'&MAKE_EPHEM='YES'&EPHEM_TYPE='VECTORS'&CENTER='@sun&START_TIME='" + startDate + "'&STOP_TIME='" + endDate + "'&STEP_SIZE='" + daysLong + "'&QUANTITIES='1,9,20,23,24,29'";
            try {
                URL locationHTTPS = new URL(urlLoc);
                HttpURLConnection connectionLoc = (HttpURLConnection) locationHTTPS.openConnection();
                connectionLoc.setRequestMethod("GET");
                connectionLoc.connect();
                System.out.println("Connection successful!");
                br = new BufferedReader(new InputStreamReader((connectionLoc.getInputStream())));

                String output = "";
                while (!output.equals("$$SOE")) {
                    output = br.readLine();
                }
                br.readLine();
                String positionalVector = br.readLine();
                entry.getValue().setCoordinates(HelperFunctions.stringToVector(positionalVector));
                String velocityVector = br.readLine() + "\n";
                entry.getValue().setVelocity(HelperFunctions.stringToVector(velocityVector));

                br.readLine();
                br.readLine();
                String positinalVectorTarget=br.readLine()+"\n";
                entry.getValue().setTargetPosition(HelperFunctions.stringToVector(positinalVectorTarget));

                System.out.println("Data added to the planet");
            } catch (MalformedURLException e) {
                System.out.println("Problems 1");
                ;
            } catch (ProtocolException e) {
                System.out.println("Problems 2");
            } catch (IOException e) {
                System.out.println("Problems 3");
            }
            urlLoc = "https://ssd.jpl.nasa.gov/api/horizons.api?format=text&COMMAND='";
        }
    }
}
