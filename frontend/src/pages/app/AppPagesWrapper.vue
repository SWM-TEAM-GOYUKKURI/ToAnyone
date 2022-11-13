<template>
  <app-navbar v-if="criticalDataLoaded" />

  <div id="app-wrapper">
    <router-view v-if="criticalDataLoaded"
                 v-slot="{ Component }">
      <v-fade-transition leave-absolute>
        <component :is="Component" />
      </v-fade-transition>
    </router-view>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import AppNavbar from "@/components/app/global/AppNavbar.vue";

@Options({
  components: {
    AppNavbar,
  },
})
export default class AppPagesWrapper extends Vue {
  criticalDataLoaded = false;

  async beforeCreate() {
    if(!this.$api.isAvailable() && this.$store.state.auth.token) {
      this.$api.registerToken(this.$store.state.auth.token);
    }

    if(!this.$store.state.user.user) {
      const response = await this.$api.getUserInfo();

      if(response.data) {
        this.$store.commit("user/updateUserInfo", response.data);
      }
    }

    // Load unread letters and save it
    if(!(await this.$api.updateUnreadLetters(this))) {
      // TEMP ALERT
      alert("편지 보관 목록을 업데이트하는 중 오류");
    }

    this.criticalDataLoaded = true;
  }
}
</script>

<style lang="scss" scoped>
#app-wrapper {
  margin-top: var(--app-navbar-height);
}
</style>
