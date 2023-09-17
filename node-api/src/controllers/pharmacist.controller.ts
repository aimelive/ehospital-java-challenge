import { Fetcher } from "../utils/fetcher";
import { Request, Response } from "express";

export class PharmacistController {
  static async get(req: Request & { token?: string }, res: Response) {
    try {
      const result = await Fetcher.get("/pharmacists", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }

  static async getMedicines(req: Request & { token?: string }, res: Response) {
    try {
      const result = await Fetcher.get("/medicines", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
  static async addMedicine(req: Request & { token?: string }, res: Response) {
    try {
      const result = await Fetcher.post("/medicines", req.body, req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
  static async giveMedicine(req: Request & { token?: string }, res: Response) {
    try {
      const result = await Fetcher.post(
        "/pharmacists/provide-medicine",
        req.body,
        req.token
      );
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
}
