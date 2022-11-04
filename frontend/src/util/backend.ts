/* eslint-disable @typescript-eslint/no-explicit-any */

import axios, { AxiosError, AxiosRequestHeaders } from "axios";
import { LetterInboxItemList } from "@/interfaces/backend";

const BE_URL: string = process.env.VUE_APP_BACKEND_URL;

export interface BECallReturn<T> {
  data?: T,
  error: boolean,
  statusCode: number,
}

export async function beGET<T>(endpoint: string, data?: Record<string, any> | null, headers?: AxiosRequestHeaders): Promise<BECallReturn<T>> {
  const result: BECallReturn<T> = {
    error: false,
    statusCode: -1,
  };

  try {
    const response = await axios.get<T>(`${BE_URL}/${endpoint}`, {
      headers,
      params: data,
    });

    result.data = response.data;
    result.statusCode = response.status;
  } catch(error) {
    result.error = true;

    if(error instanceof AxiosError && error.response) {
      result.statusCode = error.response.status;
    }
  }

  return result;
}

export async function bePOST<T>(endpoint: string, data: Record<string, any>, headers?: AxiosRequestHeaders): Promise<BECallReturn<T>> {
  const result: BECallReturn<T> = {
    error: false,
    statusCode: -1,
  };

  try {
    const response = await axios.post<T>(`${BE_URL}/${endpoint}`, data, { headers });

    result.data = response.data;
    result.statusCode = response.status;
  } catch(error) {
    result.error = true;

    if(error instanceof AxiosError && error.response) {
      result.statusCode = error.response.status;
    }
  }

  return result;
}

export async function bePUT<T>(endpoint: string, data: Record<string, any>, headers?: AxiosRequestHeaders): Promise<BECallReturn<T>> {
  const result: BECallReturn<T> = {
    error: false,
    statusCode: -1,
  };

  try {
    const response = await axios.put<T>(`${BE_URL}/${endpoint}`, data, { headers });

    result.data = response.data;
    result.statusCode = response.status;
  } catch(error) {
    result.error = true;

    if(error instanceof AxiosError && error.response) {
      result.statusCode = error.response.status;
    }
  }

  return result;
}

export function isSuccessful(statusCode: number): boolean {
  return statusCode >= 200 && statusCode <= 208;
}

export function filterUnreadLetters(...letters: LetterInboxItemList[]): LetterInboxItemList {
  const result: LetterInboxItemList = [];

  letters.forEach((l) => {
    result.push(...l.filter((i) => !i.read));
  });

  return result;
}
