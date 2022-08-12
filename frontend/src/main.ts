import { createApp } from "vue";
import VueCookies from "vue-cookies";
import App from "./App.vue";
import router from "./plugins/router";
import store from "./plugins/store";
import "./plugins/service-worker/register";

import "normalize.css";
import "./styles/global.scss";

const app = createApp(App);
app.use(store);
app.use(router);
app.use(VueCookies, {
  secure: true,
});

app.mount("#app");
