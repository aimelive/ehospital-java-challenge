import express from "express";
import { patientRoutes } from "./patient.route";
import { authRoutes } from "./auth.route";
import { AuthMiddleware } from "../middlewares/auth.middleware";
import { physicianRoutes } from "./physicians.route";
import { pharmacistRoutes } from "./pharmacist.route";

const routes = express.Router();

routes.use("/patients", AuthMiddleware.protect as any, patientRoutes);
routes.use("/physicians", AuthMiddleware.protect as any, physicianRoutes);
routes.use("/pharmacists", AuthMiddleware.protect as any, pharmacistRoutes);
routes.use("/auth", authRoutes);

export default routes;
