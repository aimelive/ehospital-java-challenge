import axios, { AxiosError } from "axios";
import { Constants } from "./constants";

const API = axios.create({
  baseURL: Constants.JAVA_BACKEND_URL,
  headers: { "Content-Type": "application/json" },
});

export class ApiException {
  statusCode: number;
  message: string;
  constructor(error: any) {
    if (error instanceof AxiosError) {
      this.message =
        error.response?.data?.message || Constants.DEFAULT_ERROR_MESSAGE;
      this.statusCode = error.response?.data?.statusCode || error.status || 500;
    } else {
      this.message = error.message || Constants.DEFAULT_ERROR_MESSAGE;
      this.statusCode = error.status || 500;
    }
  }

  toString() {
    return this.message;
  }
}

export class Fetcher {
  static async get<T = any>(url: string, token?: string) {
    try {
      const { data } = await API.get(url, {
        headers: { Authorization: token },
      });
      return data as T;
    } catch (error: any) {
      // console.log(error.message);
      throw new ApiException(error);
    }
  }

  static async post<ReturnType = any, BodyType = any>(
    url: string,
    body: BodyType,
    token?: string
  ) {
    try {
      const { data } = await API.post(url, body, {
        headers: { Authorization: token },
      });
      return data as ReturnType;
    } catch (error) {
      throw new ApiException(error);
    }
  }
}
