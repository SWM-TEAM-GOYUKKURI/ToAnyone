declare module "vue3-google-oauth2" {
  import { GoogleAuthOptions } from "./interfaces";

  /**
   * @default `options` = { scope: "profile email", prompt: "select_account" }
   */
  function install(app: any, options: GoogleAuthOptions);
}
