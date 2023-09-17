package medicine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import utils.ResponseEntity;

public class MedicineService {
    private static Map<String, Medicine> medicines = new LinkedHashMap<>();

    public static ResponseEntity<Medicine> create(Medicine newMedicine) {
        if (medicines.get(newMedicine.getMedName()) != null) {
            throw new RuntimeException("Medicine already exists");
        }
        medicines.put(newMedicine.getMedName(), newMedicine);
        saveToFile();
        return new ResponseEntity<Medicine>(200, "Medicine added successfully!", newMedicine);
    }

    public static Medicine findOne(String name) {
        return medicines.get(name);
    }

    public static Medicine[] findAll() {
        return medicines.values().toArray(new Medicine[medicines.size()]);
    }

    private static void saveToFile() {
        File file = new File("medicines.csv");
        FileWriter writer = null;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(file);

            String[] HEADER = { "medName", "medExpiration", "medPrice" };

            for (int i = 0; i < HEADER.length; i++) {
                writer.append(HEADER[i]);
                if (i < HEADER.length - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Write the medicines
            for (Map.Entry<String, Medicine> entry : medicines.entrySet()) {
                writer.append(entry.getValue().getMedName());
                writer.append(",");
                writer.append(entry.getValue().getMedExpiration());
                writer.append(",");
                writer.append(String.valueOf(entry.getValue().getMedPrice()));
                writer.append("\n");
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
