import { NextFunction, Request, Response } from "express";

export class AuthMiddleware {
  static protect(
    req: Request & { token: string },
    res: Response,
    next: NextFunction
  ) {
    try {
      const auth: string = req.headers.authorization || "";

      if (auth && auth.startsWith("Bearer ")) {
        req.token = auth;
      } else {
        throw new Error(
          "Authentication token is required, please login to continue."
        );
      }

      next();
    } catch (error: any) {
      return res.status(401).json({
        message:
          error.message || "Authentication failed, please try again later.",
      });
    }
  }
}
