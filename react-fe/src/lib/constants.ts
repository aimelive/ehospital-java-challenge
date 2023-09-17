export class Constants {
  static NODE_BACKEND_URL: string =
    import.meta.env.NODE_BACKEND_URL || "http://localhost:4000/api/v1";

  static DEFAULT_ERROR_MESSAGE: string =
    "Something went wrong, please try again later.";

  static ROLES = ["PATIENT", "PHYSICIAN", "PHARMACIST"];
}
