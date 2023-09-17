import express from "express";
import { PhysicianController } from "../controllers/physician.controller";

const physicianRoutes = express.Router();

physicianRoutes.get("/", PhysicianController.get);
physicianRoutes.post("/add-consultation", PhysicianController.addConsultation);

export { physicianRoutes };
