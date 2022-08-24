import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import AppPagesWrapper from "@/pages/app/AppPagesWrapper.vue";
import MainPage from "@/pages/app/MainPage.vue";
import LoginPage from "@/pages/app/signin/LoginPage.vue";
import LogoutPage from "@/pages/app/signin/LogoutPage.vue";
import ProfilePage from "@/pages/app/user/ProfilePage.vue";
import ProfileEditPage from "@/pages/app/user/ProfileEditPage.vue";
import LetterWritePage from "@/pages/app/letter/LetterWritePage.vue";
import LetterViewPage from "@/pages/app/letter/LetterViewPage.vue";
import LetterBoxPage from "@/pages/app/letter/LetterBoxPage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "app-wrapper",
    component: AppPagesWrapper,
    children: [
      /* Main */
      {
        path: "/",
        name: "main",
        component: MainPage,
      },

      /* Profile */
      {
        path: "profile",
        name: "profile",
        component: ProfilePage,
      },
      {
        path: "profile/edit",
        name: "profile-edit",
        component: ProfileEditPage,
      },

      /* Letter */
      {
        path: "letter/write",
        name: "letter-write",
        component: LetterWritePage,
      },
      {
        path: "letter/view",
        name: "letter-view",
        component: LetterViewPage,
        props: true,
      },
      {
        path: "letter/box",
        name: "letter-box",
        component: LetterBoxPage,
      },
    ],
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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
