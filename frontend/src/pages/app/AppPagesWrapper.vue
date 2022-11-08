<template>
  <app-navbar />

  <div id="app-wrapper">
    <router-view v-slot="{ Component }">
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
  beforeCreate(): void {
    if(!this.$api.isAvailable() && this.$store.state.auth.token) {
      this.$api.registerToken(this.$store.state.auth.token);
    }
  }

  async mounted() {
    // Load unread letters and save it
    if(!(await this.$api.updateUnreadLetters(this))) {
      // TEMP ALERT
      alert("편지 보관 목록을 업데이트하는 중 오류");
    }
  }
}
</script>

<style lang="scss" scoped>
#app-wrapper {
  margin-top: $app-navbar-height;
}
</style>
