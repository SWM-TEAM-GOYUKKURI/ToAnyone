import { Store } from "vuex";
import { StoreState } from "@/plugins/store";
import { GoogleAuthResponse } from "@/plugins/signin/google-oauth2/interfaces";

declare module "@vue/runtime-core" {
  interface ComponentCustomProperties {
    $store: Store<StoreState>,
    $gAuth: {
      instance: GoogleAuthResponse | null,
      prompt: string,
      signIn: () => Promise<GoogleAuthResponse>,
      getAuthCode: () => Promise<number>,
      signOut: () => Promise<boolean>,
    },
  }
}
