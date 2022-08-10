export interface GoogleAuthResponse {
  access_token: string,
  id_token: string,
  scope: string,
  expires_in: number,
  first_issued_at: number,
  expires_at: number
}
