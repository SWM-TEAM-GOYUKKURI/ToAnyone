import { createApp } from "vue";
import VueCookies from "vue-cookies";
import GoogleOAuth2 from "vue3-google-oauth2";
import App from "./App.vue";
import router from "./plugins/router";
import store from "./plugins/store";
import GoogleOAuth2Config from "./plugins/signin/google-oauth2/config";
import "./plugins/service-worker/register";

const app = createApp(App);
app.use(store);
app.use(router);
app.use(VueCookies, {
  secure: true,
});
app.use(GoogleOAuth2, GoogleOAuth2Config);

app.mount("#app");
