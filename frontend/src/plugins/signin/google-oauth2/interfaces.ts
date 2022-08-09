export interface GoogleAuthOptions {
  clientId: string,
  scope?: string,
  prompt?: string,
  fetch_basic_profile?: boolean,
}

export interface GoogleAuthResponse {
  access_token: string,
  id_token: string,
  scope: string,
  expires_in: number,
  first_issued_at: number,
  expires_at: number
}

export type Vue3GoogleOAuth2Component = {
  isInit: boolean,
  isAuthorized: boolean,
}
