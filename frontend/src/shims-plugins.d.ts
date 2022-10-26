/* eslint-disable @typescript-eslint/no-explicit-any */

import { Store } from "vuex";
import { RootStoreState } from "@/plugins/store";

declare module "@vue/runtime-core" {
  interface ComponentCustomProperties {
    $store: Store<RootStoreState>,
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
