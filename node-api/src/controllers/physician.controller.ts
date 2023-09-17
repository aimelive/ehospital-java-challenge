import { Fetcher } from "../utils/fetcher";
import { Request, Response } from "express";

export class PhysicianController {
  static async get(req: Request & { token?: string }, res: Response) {
    try {
      const result = await Fetcher.get("/physicians", req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
  static async addConsultation(
    req: Request & { token?: string },
    res: Response
  ) {
    try {
      const result = await Fetcher.post("/consultation", req.body, req.token);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
}
