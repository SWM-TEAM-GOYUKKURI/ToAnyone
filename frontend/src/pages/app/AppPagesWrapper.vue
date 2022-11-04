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
import { filterUnreadLetters, isSuccessful } from "@/util/backend";

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

  async created() {
    // Load unread letters and save it
    const inboxResponse = await this.$api.getInbox();
    const sentInboxResponse = await this.$api.getSentInbox();

    if(isSuccessful(inboxResponse.statusCode) && isSuccessful(sentInboxResponse.statusCode) && inboxResponse.data && sentInboxResponse.data) {
      const unreadLetters = filterUnreadLetters(inboxResponse.data, sentInboxResponse.data);

      this.$store.commit("user/updateUnreadLetters", unreadLetters);
    } else {
      // TEMP ALERT
      alert(`편지 보관 목록을 불러오는 중 오류: ${inboxResponse.statusCode}, ${sentInboxResponse.statusCode}`);
    }
  }
}
</script>

<style lang="scss" scoped>
#app-wrapper {
  margin-top: $app-navbar-height;
}
</style>
