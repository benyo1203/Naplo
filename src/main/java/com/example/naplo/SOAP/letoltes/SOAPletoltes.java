package com.example.naplo.SOAP.letoltes;


import mnb.MNBArfolyamServiceSoap;
import mnb.MNBArfolyamServiceSoapImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SOAPletoltes {
    public static void mnbArfolyamokLetolt(String fajlNev) throws Exception {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        // Fájlba írás előkészítése
        Path filePath = Paths.get(fajlNev);

        // Információk lekérése
        String info = service.getInfo();
        Files.writeString(filePath, "Szolgáltatás információ: " + info + "\n\n", java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);

        // Legfrissebb árfolyamok lekérése
        String currentRates = service.getCurrentExchangeRates();
        Files.writeString(filePath, "Legfrissebb árfolyamok: " + currentRates + "\n\n", java.nio.file.StandardOpenOption.APPEND);

        // Lekérjük az árfolyamokat egy általános lekérdezés segítségével, hogy megtaláljuk a legkisebb dátumot
        String exchangeRates = service.getExchangeRates("2000-01-01", "2024-10-31", "EUR");
        if (exchangeRates.contains("<Day")) {
            // Keresés a válaszban, hogy megállapítsuk a legkorábbi dátumot
            int startDateIndex = exchangeRates.indexOf("<Day date=\"");
            String startDate = "";
            if (startDateIndex != -1) {
                startDate = exchangeRates.substring(startDateIndex + 11, startDateIndex + 21); // Kivágjuk a dátumot
            }

            // Ha nem találtunk dátumot, használjuk az alapértelmezett dátumot
            String dynamicStartDate = startDate.isEmpty() ? "2023-01-01" : startDate;

            // A mai dátum meghatározása
            LocalDate today = LocalDate.now();
            String endDate = today.format(DateTimeFormatter.ISO_DATE);  // Formázza a mai dátumot ISO formátumban (pl. 2024-12-09)

            // Árfolyamok lekérése a dinamikusan meghatározott kezdődátummal és a mai nappal
            exchangeRates = service.getExchangeRates(dynamicStartDate, endDate, "EUR");
            Files.writeString(filePath, "Árfolyamok (" + dynamicStartDate + " - " + endDate + ") EUR valútára: " + exchangeRates + "\n\n", java.nio.file.StandardOpenOption.APPEND);
        }

        System.out.println("Adatok mentése sikeres: " + filePath.toAbsolutePath());
    }
    public static void mnbArfolyamokLetoltEgyeni(String fajlNev, String startDate, String endDate, String currencies) throws Exception {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        Path filePath = Paths.get(fajlNev);

        String exchangeRates = service.getExchangeRates(startDate, endDate, currencies);
        Files.writeString(filePath, "Árfolyamok (" + startDate + " - " + endDate + "):\n" + exchangeRates + "\n\n", java.nio.file.StandardOpenOption.CREATE);
        System.out.println("Adatok mentése sikeres: " + filePath.toAbsolutePath());
    }
}
