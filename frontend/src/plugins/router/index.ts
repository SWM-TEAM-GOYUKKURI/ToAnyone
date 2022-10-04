import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import AppPagesWrapper from "@/pages/app/AppPagesWrapper.vue";
import MainPage from "@/pages/app/MainPage.vue";
import LoginPage from "@/pages/app/signin/LoginPage.vue";
import LogoutPage from "@/pages/app/signin/LogoutPage.vue";
import SignupPersonalDataPage from "@/pages/app/signin/signup/personaldata/SignupPersonalDataPage.vue";
import BasicPersonalDataView from "@/pages/app/signin/signup/personaldata/BasicPersonalDataView.vue";
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
        meta: { title: "To. Anyone" },
        component: MainPage,
      },

      /* Profile */
      {
        path: "profile",
        name: "profile",
        meta: {
          title: "프로필",
          hideNavbarMenu: true,
        },
        component: ProfilePage,
      },
      {
        path: "profile/edit",
        name: "profile-edit",
        meta: {
          title: "프로필 수정",
          hideNavbarMenu: true,
        },
        component: ProfileEditPage,
      },

      /* Letter */
      {
        path: "letter/write",
        name: "letter-write",
        meta: { title: "편지 쓰기" },
        component: LetterWritePage,
      },
      {
        path: "letter/view",
        name: "letter-view",
        meta: { title: "편지 읽기" },
        component: LetterViewPage,
        props: true,
      },
      {
        path: "letter/box",
        name: "letter-box",
        meta: { title: "편지 보관함" },
        component: LetterBoxPage,
      },
    ],
  },

  /* Login / Logout / Signup */
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
  {
    path: "/signup/profile",
    name: "signup-profile",
    component: SignupPersonalDataPage,
    children: [
      {
        path: "basic",
        name: "signup-profile-basic",
        component: BasicPersonalDataView,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
