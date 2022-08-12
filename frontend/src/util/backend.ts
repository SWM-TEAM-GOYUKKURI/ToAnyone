import axios from "axios";

const BE_URL: string = process.env.VUE_APP_BACKEND_URL;

export async function beGET(endpoint: string) {
  return await (await axios.get(`${BE_URL}/${endpoint}`)).data as Record<string, unknown>;
}

export async function bePOST(endpoint: string, data: Record<string, unknown>) {
  return await (await axios.post(`${BE_URL}/${endpoint}`, data)).data as Record<string, unknown>;
}
