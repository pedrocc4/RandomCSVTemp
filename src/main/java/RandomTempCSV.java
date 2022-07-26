import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import static java.util.stream.IntStream.iterate;

public class RandomTempCSV {

    // Metodos auxiliares para convertir a formato csv y crear registros

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

    private static void addTemperature(String[] provincias, Random random, long minDay, long maxDay,
                                       long maxTempFallo, long minTempFallo, long registros,
                                       int totalProvincias, List<Temperatura> temperaturas) {
        iterate(0, i -> i < registros, i -> i + 1).mapToObj(i -> new Temperatura()).forEach(temperatura -> {
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            temperatura.setFecha(LocalDate.ofEpochDay(randomDay));
            temperatura.setTemperatura(random.nextLong(maxTempFallo - minTempFallo) + minTempFallo);
            temperatura.setProvincia(provincias[random.nextInt(totalProvincias)]);
            temperaturas.add(temperatura);
        });
    }

    public static void main(String[] args) throws IOException {

        // constantes
        final Logger log = Logger.getLogger(String.valueOf(RandomTempCSV.class));
        String[] provincias = new String[]{"Alava",
                "Albacete", "Alicante", "Almeria", "Asturias", "Avila", "Badajoz", "Barcelona", "Burgos", "Caceres",
                "Cadiz", "Cantabria", "Castellon", "Ciudad Real", "Cordoba", "La Coruna", "Cuenca", "Gerona", "Granada",
                "Guadalajara", "Guipuzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaen",
                "Leon", "Lerida", "Lugo", "Madrid", "Malaga", "Murcia", "Navarra",
                "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Sevilla",
                "Soria", "Tarragona", "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya",
                "Zamora", "Zaragoza"};

        Random random = new Random();
        long minDay = LocalDate.of(2000, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2022, 1, 1).toEpochDay();
        long maxTemp = 50;
        long minTemp = -20;
        long maxTempFallo = 100;
        long minTempFallo = -100;
        long registros = 1000;
        long registrosFallo = 50;
        int totalProvincias = provincias.length;

        List<Temperatura> temperaturas = new ArrayList<>();

        // introducimos las correctas (registros)
        addTemperature(provincias, random, minDay, maxDay, maxTemp, minTemp, registros,
                totalProvincias, temperaturas);

        log.info("Primeras " + registros + " completados");

        // creamos la mitad de las incorrectas negativamente
        addTemperature(provincias, random, minDay, maxDay, minTemp, minTempFallo, registrosFallo / 2,
                totalProvincias, temperaturas);

        log.info("Registros: " + registrosFallo / 2 + " incorrectos completados");

        // creamos la mitad de las incorrectas positivamente
        addTemperature(provincias, random, minDay, maxDay, maxTempFallo, maxTemp, registrosFallo / 2,
                totalProvincias, temperaturas);

        log.info("Registros: " + registrosFallo / 2 + " incorrectos completados");

        // obtencion de csv
        exportCSV(temperaturas, "prueba", "Provincia;Temperatura;Fecha\n");

        log.info("CSV completado");
    }

}
