import { reactive } from "vue";
import waitFor from "@/util/script-waiter";

const loadState = reactive({ value: false });

function load() {
  const gsiScript = document.createElement("script");
  gsiScript.src = "https://accounts.google.com/gsi/client";
  gsiScript.async = true;
  gsiScript.defer = true;

  document.body.appendChild(gsiScript);

  gsiScript.addEventListener("load", async () => {
    if(await waitFor("google")) {
      console.log("Google loaded");
      loadState.value = true;
    }
  });
}

export default {
  load,
  loadState,
};
