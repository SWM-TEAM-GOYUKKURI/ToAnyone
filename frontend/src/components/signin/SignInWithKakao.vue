<template>
  <div id="login-kakao" class="button-login">카카오로 로그인 (준비 중)</div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class SignInWithKakao extends Vue {
  // TODO: just a placeholder; will be implemented in stage 2

  async loginCallback() {
    // to be filled
  }
}
</script>

<style lang="scss">
/* 임시 버튼 스타일 */
.button-login {
  display: inline-block;
  padding: 0.5em;
  background-color: rgba(0, 0, 0, 0.25);
  cursor: pointer;
}
</style>
