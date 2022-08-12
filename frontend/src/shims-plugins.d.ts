import { Store } from "vuex";
import { StoreState } from "@/plugins/store";

declare module "@vue/runtime-core" {
  interface ComponentCustomProperties {
    $store: Store<StoreState>,
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
