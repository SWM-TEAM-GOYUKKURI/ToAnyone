import { Store } from "vuex";
import { StoreState } from "@/plugins/store";

declare module "@vue/runtime-core" {
  interface ComponentCustomProperties {
    $store: Store<StoreState>,
  }
}
