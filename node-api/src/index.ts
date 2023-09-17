import express, { Application } from "express";
import { Request, Response } from "express";

import * as dotenv from "dotenv";
import { Fetcher } from "./utils/fetcher";
import routes from "./routes";
import cors from "cors";

dotenv.config();

const app: Application = express();

const PORT = process.env.PORT || 3000;

app.use(express.json());

app.use(cors());

app.get("/", async (req: Request, res: Response) => {
  try {
    const result = await Fetcher.get("/hello");
    return res.status(result.statusCode).json(result);
  } catch (error: any) {
    return res.status(error.statusCode).json(error);
  }
});

app.use("/api/v1", routes);

app.get("*", (req: Request, res: Response) => {
  return res.status(404).json({
    statusCode: 404,
    message: "Path not found!",
    description: "Path you're looking for does not exist!",
  });
});

app.listen(PORT, (): void => {
  console.log(
    "Server running on port " + PORT + " 🔥\nPath: http://localhost:" + PORT
  );
});
