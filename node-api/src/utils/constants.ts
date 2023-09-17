import * as dotenv from "dotenv";

dotenv.config();

export class Constants {
  static JAVA_BACKEND_URL: string =
    process.env.JAVA_BACKEND_URL || "http://localhost:8080/api/v1";

  static DEFAULT_ERROR_MESSAGE: string =
    "Something went wrong, please try again later.";
}
