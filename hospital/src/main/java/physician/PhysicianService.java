package physician;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class PhysicianService {
    private static Map<String, Physician> physicians = new LinkedHashMap<>();

    public static void create(Physician physician) {
        if (physicians.get(physician.getEmail()) != null) {
            throw new RuntimeException("Physician already registered.");
        }
        physicians.put(physician.getEmail(), physician);
    }

    public static Physician findByEmail(String email) {
        return physicians.get(email);
    }

    public static Physician[] findAll() {
        List<Physician> physiciansData = new ArrayList<Physician>(physicians.values());

        Comparator<Physician> nameComparator = new Comparator<Physician>() {
            @Override
            public int compare(Physician physician1, Physician physician2) {
                return physician1.getName().compareTo(physician2.getName());
            }
        };
        Physician[] physiciansList = physicians.values().toArray(new Physician[physiciansData.size()]);

        Arrays.sort(physiciansList, nameComparator);

        return physiciansList;
    }
}
