import axios, { AxiosRequestHeaders } from "axios";

const BE_URL: string = process.env.VUE_APP_BACKEND_URL;

export async function beGET(endpoint: string, data?: Record<string, unknown> | null, headers?: AxiosRequestHeaders): Promise<{ statusCode: number, data: Record<string, unknown> }> {
  const response = await axios.get(`${BE_URL}/${endpoint}`, {
    headers,
    params: data,
  });

  return {
    statusCode: response.status,
    data: await response.data as Record<string, unknown>,
  };
}

export async function bePOST(endpoint: string, data: Record<string, unknown>, headers?: AxiosRequestHeaders): Promise<{ statusCode: number, data: Record<string, unknown> }> {
  const response = await axios.post(`${BE_URL}/${endpoint}`, data, { headers });

  return {
    statusCode: response.status,
    data: await response.data as Record<string, unknown>,
  };
}
