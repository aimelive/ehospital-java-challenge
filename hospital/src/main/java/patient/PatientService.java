package patient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import consultation.Consultation;
import medicine.Medicine;
import pharmacist.Pharmacist;
import physician.Physician;

public class PatientService {
    private static Map<String, Patient> patients = new LinkedHashMap<>();

    public static void create(Patient patient) {
        if (patients.get(patient.getUsername()) != null) {
            throw new RuntimeException(
                    "Patient " + patient.getUsername() + " already registered. Try a different username.");
        }
        patients.put(patient.getUsername(), patient);
    }

    public static Patient findByUsername(String username) {
        return patients.get(username);
    }

    public static Patient[] findAll() {
        List<Patient> patientsData = new ArrayList<Patient>(patients.values());
        return patients.values().toArray(new Patient[patientsData.size()]);
    }

    // Select physician
    public static Patient selectPhysician(String patientUsername, Physician physician) {

        Patient patient = patients.get(patientUsername);
        patient.setSelectedPhysician(physician);
        patients.put(patientUsername, patient);

        return patient;
    }

    // Select pharmacist
    public static Patient selectPharmacist(String patientUsername, Pharmacist pharmacist) {

        Patient patient = patients.get(patientUsername);
        patient.setSelectedPharmacist(pharmacist);
        patients.put(patientUsername, patient);

        return patient;
    }

    // Get all patients by physician
    public static Patient[] getPatientsByPhysician(Physician physician) {
        List<Patient> patientsData = new ArrayList<Patient>();
        for (Patient patient : patients.values()) {
            Physician selectedPhysician = patient.getSelectedPhysician();
            if (selectedPhysician == null) {
                continue;
            } else if (selectedPhysician.equals(physician)) {
                patientsData.add(patient);
            }
        }

        return patientsData.toArray(new Patient[patientsData.size()]);
    }

    // Get all patients by pharmacists
    public static Patient[] getPatientsByPharmacist(Pharmacist pharmacist) {
        List<Patient> patientList = new ArrayList<Patient>();
        for (Patient patient : patients.values()) {
            Pharmacist selectedPharmacist = patient.getSelectedPharmacist();
            if (selectedPharmacist == null) {
                continue;
            } else if (selectedPharmacist.equals(pharmacist)) {
                patientList.add(patient);
            }
        }

        return patientList.toArray(new Patient[patientList.size()]);
    }

    // Assign consultation
    public static Patient assignConsultation(String patientUsername, Consultation consultation)
            throws AuthenticationException {
        Patient patient = patients.get(patientUsername);
        patient.setConsultation(consultation);
        patients.put(patientUsername, patient);
        return patient;
    }

    // GET all medicines of a patient
    public static Medicine[] findMedicinesByPatient(Patient patient) {
        return patient.getMedicines();
    }

    // GET consultation by patient
    public static Consultation getConsultationByPatient(Patient patient) {
        return patient.getConsultation();
    }

    // GET medicines by patient
    public static Medicine[] getMedicinesByPatient(Patient patient) {
        return patient.getMedicines();
    }

    // Add medicine to patient
    public static Patient addMedicine(String patientUsername, Medicine medicine) {

        Patient patient = patients.get(patientUsername);
        patient.assignNewMedicine(medicine);

        patients.put(patientUsername, patient);

        return patient;
    }

}
