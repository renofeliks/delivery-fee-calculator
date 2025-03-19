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

@Component
public class WeatherDataFetcher {
    private final WeatherDataRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String WEATHER_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    public WeatherDataFetcher(WeatherDataRepository repository) {
        this.repository = repository;
    }

    // To run every hour at HH:15:00
    // @Scheduled(cron = "0 15 * * * ?")
    @Scheduled(cron = "0 * * * * ?")
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

    private void parseAndSaveWeatherData(String xmlData) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlData.getBytes(StandardCharsets.UTF_8)));
            doc.getDocumentElement().normalize();
            NodeList stations = doc.getElementsByTagName("station");

            for (int i = 0; i < stations.getLength(); i++) {
                Node node = stations.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String stationName = element.getElementsByTagName("name").item(0).getTextContent();

                    if (!stationName.equals("Tallinn-Harku") &&
                            !stationName.equals("Tartu-Tõravere") &&
                            !stationName.equals("Pärnu")) {
                        continue; // Skip other stations
                    }

                    String wmoCode = element.getElementsByTagName("wmocode").item(0).getTextContent();
                    double airTemp = Double.parseDouble(element.getElementsByTagName("airtemperature").item(0).getTextContent());
                    double windSpeed = Double.parseDouble(element.getElementsByTagName("windspeed").item(0).getTextContent());
                    String weatherPhenomenon = element.getElementsByTagName("phenomenon").item(0) != null
                            ? element.getElementsByTagName("phenomenon").item(0).getTextContent()
                            : "Clear";

                    WeatherData weatherData = new WeatherData(stationName, wmoCode, airTemp, windSpeed, weatherPhenomenon, LocalDateTime.now());
                    repository.save(weatherData);
                }
            }
            System.out.println("Successfully updated data at " + LocalDateTime.now());
        } catch (Exception e) {
            System.out.println("Error parsing weather data: " + e.getMessage());
        }
    }
}
