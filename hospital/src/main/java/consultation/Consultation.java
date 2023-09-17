package consultation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import physician.Physician;

@Setter
@Getter
@AllArgsConstructor
public class Consultation {
    private String disease;
    private String description;
    private Physician physician;
}
