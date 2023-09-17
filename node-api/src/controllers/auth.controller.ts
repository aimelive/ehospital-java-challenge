import { Fetcher } from "../utils/fetcher";
import { Request, Response } from "express";

export class AuthController {
  static async register(req: Request, res: Response) {
    try {
      const result = await Fetcher.post("/register", req.body);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }

  static async login(req: Request, res: Response) {
    try {
      const result = await Fetcher.post("/login", req.body);
      return res.status(result.statusCode).json(result);
    } catch (error: any) {
      return res.status(error.statusCode).json(error);
    }
  }
}
