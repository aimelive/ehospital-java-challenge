import express from "express";
import { PharmacistController } from "../controllers/pharmacist.controller";

const pharmacistRoutes = express.Router();

pharmacistRoutes.get("/", PharmacistController.get);
pharmacistRoutes.get("/medicines", PharmacistController.getMedicines);
pharmacistRoutes.post("/add-medicine", PharmacistController.addMedicine);
pharmacistRoutes.post("/give-medicine", PharmacistController.giveMedicine);

export { pharmacistRoutes };
