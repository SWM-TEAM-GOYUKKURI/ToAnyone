<template>
  <div class="home">
    <profile-area-small :nickname="$store.state.auth.userBasicInfo.nickname"
                        :profileImageUrl="'https://picsum.photos/seed/toanyone/300'"/>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import ProfileAreaSmall from "@/components/profile/ProfileAreaSmall.vue";

@Options({
  components: {
    ProfileAreaSmall,
  },
})
export default class MainPage extends Vue {}
</script>
