package pharmacist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class PharmacistService {
    private static Map<String, Pharmacist> pharmacists = new LinkedHashMap<>();

    public static void create(Pharmacist pharmacist) {
        if (pharmacists.get(pharmacist.getPhoneNumber()) != null) {
            throw new RuntimeException("Pharmacist phone number is already in use. Please use a different one.");
        }
        pharmacists.put(pharmacist.getPhoneNumber(), pharmacist);
    }

    public static Pharmacist findByPhoneNumber(String phoneNumber) {
        return pharmacists.get(phoneNumber);
    }

    public static Pharmacist[] findAll() {
        List<Pharmacist> pharmacistsData = new ArrayList<Pharmacist>(pharmacists.values());

        Comparator<Pharmacist> ageComparator = new Comparator<Pharmacist>() {
            @Override
            public int compare(Pharmacist pharmacist1, Pharmacist pharmacist2) {
                return pharmacist1.getAge().compareTo(pharmacist2.getAge());
            }
        };

        Pharmacist[] pharmacistsList = pharmacists.values().toArray(new Pharmacist[pharmacistsData.size()]);

        Arrays.sort(pharmacistsList, ageComparator);

        return pharmacistsList;
    }
}
