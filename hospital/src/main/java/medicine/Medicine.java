package medicine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Medicine {
    private String medName;
    private String medPrice;
    private String medExpiration;
}
