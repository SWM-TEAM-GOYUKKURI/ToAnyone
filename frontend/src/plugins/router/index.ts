import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainPage from "@/pages/MainPage.vue";
import LoginPage from "@/pages/signin/LoginPage.vue";
import LogoutPage from "@/pages/signin/LogoutPage.vue";
import ProfilePage from "@/pages/user/ProfilePage.vue";
import ProfileEditPage from "@/pages/user/ProfileEditPage.vue";
import LetterWritePage from "@/pages/letter/LetterWritePage.vue";
import LetterViewPage from "@/pages/letter/LetterViewPage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "main",
    component: MainPage,
  },

  /* Login / Logout */
  {
    path: "/login",
    name: "login",
    component: LoginPage,
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutPage,
  },

  /* Profile */
  {
    path: "/profile",
    name: "profile",
    component: ProfilePage,
  },
  {
    path: "/profile/edit",
    name: "profile-edit",
    component: ProfileEditPage,
  },

  /* Letter */
  {
    path: "/letter/write",
    name: "letter-write",
    component: LetterWritePage,
  },
  {
    path: "/letter/view",
    name: "letter-view",
    component: LetterViewPage,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
