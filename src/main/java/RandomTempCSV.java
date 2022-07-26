import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTempCSV {
    private static void exportCSV(List<Temperatura> datos, String name, String header) throws IOException {
        FileOutputStream csvOutputFile = new FileOutputStream(name + ".csv");
        csvOutputFile.write(header.getBytes());
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            datos.stream()
                    .map(RandomTempCSV::convertToCSV)
                    .forEach(pw::println);
        }
    }

    private static String convertToCSV(Temperatura temperatura) {
        return temperatura.toString();
    }

    public static void main(String[] args) throws IOException {

        Random random = new Random();

        String[] provincias = new String[]{"Alava",
                "Albacete", "Alicante", "Almería", "Asturias", "Avila", "Badajoz", "Barcelona", "Burgos", "Cáceres",
                "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara",
                "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra",
                "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona",
                "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"};

        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2022, 1, 1).toEpochDay();
        long maxTemp = 100;
        long minTemp = -100;
        List<Temperatura> temperaturas = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Temperatura temperatura = new Temperatura();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            temperatura.setFecha(LocalDate.ofEpochDay(randomDay));
            temperatura.setTemperatura(random.nextLong(maxTemp - minTemp) + minTemp);
            temperatura.setProvincia(provincias[random.nextInt(provincias.length)]);
            temperaturas.add(temperatura);
        }

        exportCSV(temperaturas, "prueba", "Provincia;Temperatura;Fecha\n");
    }
}
