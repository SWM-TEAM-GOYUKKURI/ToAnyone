import { reactive } from "vue";
import waitFor from "@/util/script-waiter";

export interface GoogleAuthResponse {
  clientId: string,
  credential: string,
  select_by: string,
}

const loadState = reactive({ value: false });
const gsiScript = document.createElement("script");

async function scriptLoadHandler() {
  if(await waitFor("google")) {
    console.log("Google loaded");
    loadState.value = true;
  }
}

function load() {
  gsiScript.src = "https://accounts.google.com/gsi/client";
  gsiScript.async = true;
  gsiScript.defer = true;

  document.body.appendChild(gsiScript);

  gsiScript.addEventListener("load", scriptLoadHandler);
}

function unload() {
  gsiScript.removeAttribute("src");
  gsiScript.removeEventListener("load", scriptLoadHandler);
  gsiScript.remove();
  window.google = undefined;

  // Cleanup autoloaded resources
  document.getElementById("googleidentityservice_button_styles")?.remove();
  document.getElementById("googleidentityservice")?.remove();
  document.getElementById("g_a11y_announcement")?.remove();

  loadState.value = false;
}

export default {
  load,
  unload,
  loadState,
};
