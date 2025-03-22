package com.fuijtsu.project.delivery_fee_calculator.scheduler;

import com.fuijtsu.project.delivery_fee_calculator.model.WeatherData;
import com.fuijtsu.project.delivery_fee_calculator.repository.WeatherDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Component is responsible for fetching and storing weather data.
 * Data is fetched from Estonian Weather Service XML API and is stored into the database
 * using the WeatherDataRepository class.
 */
@Component
public class WeatherDataFetcher {
    private final WeatherDataRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String WEATHER_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    /**
     * @param repository - Used to save weather data.
     */
    public WeatherDataFetcher(WeatherDataRepository repository) {
        this.repository = repository;
    }



    /**
     * Scheduled method to run every hour at HH:15.
     * Fetched the latest weather data from API and processes it.
     */
    // @Scheduled(cron = "0 * * * * ?") // Was used for testing. (Runs every minute)
    @Scheduled(cron = "0 15 * * * ?")
    public void fetchWeatherData() {
        try {
            String response = restTemplate.getForObject(WEATHER_API_URL, String.class);
            if (response != null && !response.trim().isEmpty()) {
                parseAndSaveWeatherData(response);
            } else {
                System.err.println("Error: Empty response received from API");
            }
        } catch (Exception e) {
            System.err.println("Error fetching weather data: " + e.getMessage());
        }
    }

    /**
     * Parses the given XML data and stores weather data for specified stations.
     * @param xmlData - XML response from the API.
     */
    private void parseAndSaveWeatherData(String xmlData) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Converts XML string into DOM Document.
            Document doc = builder.parse(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));
            doc.getDocumentElement().normalize();

            // In XML document, the structure is represented as a tree of nodes.
            NodeList stations = doc.getElementsByTagName("station");

            for (int i = 0; i < stations.getLength(); i++) {
                Node node = stations.item(i);

                // Only process element nodes.
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // Cast it to Element to use its methods.
                    Element element = (Element) node;
                    String stationName = element.getElementsByTagName("name").item(0).getTextContent();

                    // Skip other stations
                    if (!stationName.equals("Tallinn-Harku") && !stationName.equals("Tartu-Tõravere") && !stationName.equals("Pärnu")) {
                        continue;
                    }

                    // Get required values from XML.
                    String wmoCode = element.getElementsByTagName("wmocode").item(0).getTextContent();
                    double airTemp = Double.parseDouble(element.getElementsByTagName("airtemperature").item(0).getTextContent());
                    double windSpeed = Double.parseDouble(element.getElementsByTagName("windspeed").item(0).getTextContent());
                    String weatherPhenomenon = element.getElementsByTagName("phenomenon").item(0).getTextContent();

                    // Create WeatherData entity and save it.
                    WeatherData weatherData = new WeatherData(
                            stationName,
                            wmoCode,
                            airTemp,
                            windSpeed,
                            weatherPhenomenon,
                            LocalDateTime.now()
                    );
                    repository.save(weatherData);
                }
            }
            System.out.println("Successfully updated data at " + LocalDateTime.now());
        } catch (Exception e) {
            System.out.println("Error parsing weather data: " + e.getMessage());
        }
    }
}
