import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainPage from "@/pages/app/MainPage.vue";
import LoginPage from "@/pages/app/signin/LoginPage.vue";
import LogoutPage from "@/pages/app/signin/LogoutPage.vue";
import ProfilePage from "@/pages/app/user/ProfilePage.vue";
import ProfileEditPage from "@/pages/app/user/ProfileEditPage.vue";
import LetterWritePage from "@/pages/app/letter/LetterWritePage.vue";
import LetterViewPage from "@/pages/app/letter/LetterViewPage.vue";

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
