/* eslint-disable @typescript-eslint/no-explicit-any */

import axios, { AxiosRequestHeaders } from "axios";

const BE_URL: string = process.env.VUE_APP_BACKEND_URL;

export async function beGET<T>(endpoint: string, data?: Record<string, any> | null, headers?: AxiosRequestHeaders): Promise<{ statusCode: number, data: T }> {
  const response = await axios.get<T>(`${BE_URL}/${endpoint}`, {
    headers,
    params: data,
  });

  return {
    statusCode: response.status,
    data: response.data,
  };
}

export async function bePOST<T>(endpoint: string, data: Record<string, any>, headers?: AxiosRequestHeaders): Promise<{ statusCode: number, data: T }> {
  const response = await axios.post<T>(`${BE_URL}/${endpoint}`, data, { headers });

  return {
    statusCode: response.status,
    data: response.data,
  };
}
