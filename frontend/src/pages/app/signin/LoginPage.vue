<template>
  <div>
    <span>{{ loadState }}</span>
    <sign-in-with-google :loaded="googleLoadState" />
    <sign-in-with-kakao />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import googleSigninScript from "@/plugins/signin/google/index";
import SignInWithGoogle from "@/components/signin/SignInWithGoogle.vue";
import SignInWithKakao from "@/components/signin/SignInWithKakao.vue";

@Options({
  components: {
    SignInWithGoogle,
    SignInWithKakao,
  },
})
export default class LoginPage extends Vue {
  get googleLoadState(): boolean {
    return googleSigninScript.loadState.value;
  }

  created() {
    googleSigninScript.load();
  }
}
</script>
