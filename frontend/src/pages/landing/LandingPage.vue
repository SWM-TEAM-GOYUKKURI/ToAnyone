<template>
  <v-slide-y-transition>
    <in-app-dialog v-show="$route.name === 'login'"
                   id="login-dialog">
      <router-view />
    </in-app-dialog>
  </v-slide-y-transition>

  <div id="landing-wrapper">
    <section class="landing__main">
      <div class="landing__main__title">To. Anyone</div>
      <div class="landing__main__subtitle">📫 마음 속 이야기를 나누는 편지를 주고받아보세요.</div>

      <div class="landing__main__buttons">
        <router-link :to="{ name: 'login' }">
          <button class="landing__main__buttons__login button primary"><v-icon>mdi-login</v-icon> <span>로그인</span></button>
        </router-link>
      </div>
    </section>

    <footer class="landing__footer">
      <div>Copyright ⓒ TEAM GOYUKKURI <small><a href="https://swmaestro.org/" target="_blank">(from SW Maestro)</a></small>, All rights reserved.</div>
    </footer>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import * as GoogleLogin from "@/plugins/signin/google";
import InAppDialog from "@/components/InAppDialog.vue";

@Options({
  components: {
    InAppDialog,
  },
})
export default class LandingPage extends Vue {
  created(): void {
    GoogleLogin.load();
  }

  unmounted(): void {
    GoogleLogin.unload();
  }
}
</script>

<style lang="scss" scoped>
#landing-wrapper {
  .landing {
    &__main {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 100vh;

      &__title {
        font-size: 4em;
        font-weight: bold;
      }

      &__subtitle {
        font-size: 1.5em;
        margin-top: 1em;
      }

      &__buttons {
        margin-top: 2em;

        &__login {
          padding-left: 4em;
          padding-right: 4em;
        }
      }
    }

    &__footer {
      padding: 3em 1em;
      background: $color-dark;

      & > * {
        opacity: 0.8;
      }

      a { text-decoration: underline; }
    }
  }
}
</style>
