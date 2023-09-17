import { Fetcher } from "../utils/fetcher";
import { Request, Response } from "express";

export class PatientController {
  static async get(req: Request & { token?: string }, res: Response) {
    try {
      const result = await Fetcher.get("/patients", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
  static async findByPharmacist(
    req: Request & { token?: string },
    res: Response
  ) {
    try {
      const result = await Fetcher.get("/patients/findByPharmacist", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
  static async findByPhysician(
    req: Request & { token?: string },
    res: Response
  ) {
    try {
      const result = await Fetcher.get("/patients/findByPhysician", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }

  static async findByPatientMedicines(
    req: Request & { token?: string },
    res: Response
  ) {
    try {
      const result = await Fetcher.get("/medicines/findByPatient", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
  static async findByPatientConsultations(
    req: Request & { token?: string },
    res: Response
  ) {
    try {
      const result = await Fetcher.get("/consultation", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
  static async selectPhysician(
    req: Request & { token?: string },
    res: Response
  ) {
    try {
      if (!req.query.email) {
        return res.status(400).json({ message: "Physician email is required" });
      }
      const result = await Fetcher.post(
        "/physicians/select?email=" + req.query.email,
        {},
        req.token
      );
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode || 500).json(error);
    }
  }

  static async selectPharmacist(
    req: Request & { token?: string },
    res: Response
  ) {
    try {
      if (!req.query.phoneNumber) {
        return res
          .status(400)
          .json({ message: "Pharmacist phone number is required" });
      }
      const result = await Fetcher.post(
        "/pharmacists/select?phoneNumber=" + req.query.phoneNumber,
        {},
        req.token
      );
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
}
