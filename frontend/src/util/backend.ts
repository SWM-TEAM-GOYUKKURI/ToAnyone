/* eslint-disable @typescript-eslint/no-explicit-any */

import axios, { AxiosRequestHeaders } from "axios";

const BE_URL: string = process.env.VUE_APP_BACKEND_URL;

interface BECallReturn<T> {
  statusCode: number,
  data: T,
}

export async function beGET<T>(endpoint: string, data?: Record<string, any> | null, headers?: AxiosRequestHeaders): Promise<BECallReturn<T>> {
  const response = await axios.get<T>(`${BE_URL}/${endpoint}`, {
    headers,
    params: data,
  });

  return {
    statusCode: response.status,
    data: response.data,
  };
}

export async function bePOST<T>(endpoint: string, data: Record<string, any>, headers?: AxiosRequestHeaders): Promise<BECallReturn<T>> {
  const response = await axios.post<T>(`${BE_URL}/${endpoint}`, data, { headers });

  return {
    statusCode: response.status,
    data: response.data,
  };
}

export async function bePUT<T>(endpoint: string, data: Record<string, any>, headers?: AxiosRequestHeaders): Promise<BECallReturn<T>> {
  const response = await axios.put<T>(`${BE_URL}/${endpoint}`, data, { headers });

  return {
    statusCode: response.status,
    data: response.data,
  };
}

export function isSuccessful(statusCode: number): boolean {
  return statusCode >= 200 && statusCode <= 208;
}
