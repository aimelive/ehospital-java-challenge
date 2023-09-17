import express from "express";
import { PatientController } from "../controllers/patient.controller";

const patientRoutes = express.Router();

patientRoutes.get("/", PatientController.get);

patientRoutes.get("/findByPharmacist", PatientController.findByPharmacist);
patientRoutes.get("/findByPhysician", PatientController.findByPhysician);

patientRoutes.get(
  "/medicines/findByPatient",
  PatientController.findByPatientMedicines
);
patientRoutes.get(
  "/consultation",
  PatientController.findByPatientConsultations
);

patientRoutes.post("/select-physician", PatientController.selectPhysician);

patientRoutes.post("/select-pharmacist", PatientController.selectPharmacist);

export { patientRoutes };
