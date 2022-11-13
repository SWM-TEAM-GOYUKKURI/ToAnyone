<template>
  <router-view v-slot="{ Component }">
    <v-slide-y-transition>
      <in-app-dialog v-if="$route.name === 'login'"
                     id="login-dialog"
                     :fullscreenOnVPSmall="true">
        <component :is="Component" />
      </in-app-dialog>
    </v-slide-y-transition>
  </router-view>

  <div id="landing-wrapper">
    <section class="landing__main">
      <div class="landing__main__title">To. Anyone</div>
      <div class="landing__main__subtitle">📫 마음 속 이야기를 나누는 편지를 주고받아보세요.</div>

      <div class="landing__main__buttons">
        <router-link :to="{ name: 'login' }">
          <button class="landing__main__buttons__login button primary"><v-icon>mdi-login</v-icon> <span>로그인</span></button>
        </router-link>
      </div>

      <div class="landing__main__bgcredit">Image by <a href="https://pixabay.com/users/andrys-439428" target="_blank">Andrys Stienstra</a> from <a href="https://pixabay.com" target="_blank">Pixabay</a></div>
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
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      width: 100%;
      height: 100vh;

      background: url("@/assets/bg/bg-letter-pixabay-andrys_stienstra-darken.jpg");
      background-size: cover;
      background-position: center center;
      backdrop-filter: brightness(50%);

      & * { text-align: center; }

      &__title {
        font-size: 4em;
        font-weight: bold;

        @media (max-width: $viewport-small-max-width) { font-size: 3em; }
      }

      &__subtitle {
        font-size: 1.5em;
        margin-top: 1em;

        @media (max-width: $viewport-small-max-width) { font-size: 1.25em; }
      }

      &__buttons {
        margin-top: 2em;

        &__login {
          padding-left: 4em;
          padding-right: 4em;
        }
      }

      &__bgcredit {
        position: absolute;
        bottom: 1.5em;
        right: 1.5em;
        font-size: 0.66em;
        opacity: 0.75;

        a { text-decoration: underline; }
      }
    }

    &__footer {
      font-size: 0.9em;
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
