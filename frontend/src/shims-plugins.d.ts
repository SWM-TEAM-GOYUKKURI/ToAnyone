/* eslint-disable @typescript-eslint/no-explicit-any */

import { Store } from "vuex";
import { RootStoreState } from "@/plugins/store";
import APICaller from "@/plugins/be-api-caller";

declare module "@vue/runtime-core" {
  interface ComponentCustomProperties {
    $store: Store<RootStoreState>,
    $api: APICaller,
  }
}

declare global {
  interface Window {
    google?: {
      accounts: {
        id: Record<string, any>,
        oauth2: Record<string, any>,
      }
    }
  }
}
