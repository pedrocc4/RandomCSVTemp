import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temperatura {
    private String provincia;
    private long temperatura;
    private LocalDate fecha;

    @Override
    public String toString() {
        return provincia + "," + temperatura + "," + fecha;
    }
}
