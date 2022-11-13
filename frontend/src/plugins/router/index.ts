import { createRouter, createWebHistory, RouteLocationNormalized, RouteRecordRaw } from "vue-router";
import LandingPage from "@/pages/landing/LandingPage.vue";
import LandingLoginView from "@/pages/landing/LandingLoginView.vue";
import AppPagesWrapper from "@/pages/app/AppPagesWrapper.vue";
import MainPage from "@/pages/app/MainPage.vue";
import LogoutPage from "@/pages/app/signin/LogoutPage.vue";
import SignupPersonalDataPage from "@/pages/app/signin/signup/personaldata/SignupPersonalDataPage.vue";
import BasicPersonalDataView from "@/pages/app/signin/signup/personaldata/BasicPersonalDataView.vue";
import MentalHealthSurveyView from "@/pages/app/signin/signup/personaldata/MentalHealthSurveyView.vue";
import ItemStorePage from "@/pages/app/store/ItemStorePage.vue";
import ItemStoreItemsView from "@/pages/app/store/ItemStoreItemsView.vue";
import ProfilePage from "@/pages/app/user/ProfilePage.vue";
import ProfileEditPage from "@/pages/app/user/ProfileEditPage.vue";
import PointHelpView from "@/pages/app/user/PointHelpView.vue";
import LetterWritePage from "@/pages/app/letter/LetterWritePage.vue";
import LetterViewPage from "@/pages/app/letter/LetterViewPage.vue";
import LetterBoxPage from "@/pages/app/letter/LetterBoxPage.vue";

const routes: Array<RouteRecordRaw> = [
  /* Landing */
  {
    path: "/hello",
    name: "landing",
    component: LandingPage,
    children: [
      {
        path: "/login",
        name: "login",
        component: LandingLoginView,
      },
    ],
  },

  /* Root */
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
        meta: {
          title: "프로필",
          hideNavbarMenu: true,
        },
        component: ProfilePage,
        children: [
          {
            path: "edit",
            name: "profile-edit",
            meta: {
              title: "프로필",
              hideNavbarMenu: true,
            },
            component: ProfileEditPage,
          },
          {
            path: "pointhelp",
            name: "point-help",
            meta: {
              title: "프로필",
              hideNavbarMenu: true,
            },
            component: PointHelpView,
          },
        ],
      },

      /* Letter */
      {
        path: "letter/write",
        name: "letter-write",
        meta: { title: "편지 쓰기" },
        component: LetterWritePage,
      },
      {
        path: "letter/reply",
        name: "letter-reply",
        meta: { title: "편지 답장하기" },
        component: LetterWritePage,
        props: true,
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

      /* Item Store */
      {
        path: "store",
        name: "item-store",
        meta: { title: "아이템 상점" },
        component: ItemStorePage,
        children: [
          {
            path: "stickers",
            name: "item-store-stickers",
            meta: {
              title: "아이템 상점",
              type: "stickers",
            },
            component: ItemStoreItemsView,
          },
          {
            path: "fonts",
            name: "item-store-fonts",
            meta: {
              title: "아이템 상점",
              type: "fonts",
            },
            component: ItemStoreItemsView,
          },
          {
            path: "papers",
            name: "item-store-papers",
            meta: {
              title: "아이템 상점",
              type: "papers",
            },
            component: ItemStoreItemsView,
          },
        ],
      },
    ],
  },

  /* Logout / Signup */
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
      {
        path: "survey",
        name: "signup-profile-survey",
        component: MentalHealthSurveyView,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.afterEach((to: RouteLocationNormalized) => {
  if(to.meta.title) {
    document.title = `To. Anyone - ${to.meta.title}`;
  } else {
    document.title = "To. Anyone";
  }
});

export default router;
