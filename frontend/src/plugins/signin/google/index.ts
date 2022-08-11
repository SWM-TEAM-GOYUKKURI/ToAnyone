import { reactive } from "vue";
import waitFor from "@/util/script-waiter";

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
  loadState.value = false;
}

export default {
  load,
  unload,
  loadState,
};
