<template>
  <v-slide-y-reverse-transition>
    <div id="profile-edit-page-wrapper"
         v-show="show">
      <div class="profile-edit__header">
        <h1>프로필 수정</h1>
        <a href="#"
           title="닫기"
           @click="$router.back()">
          <v-icon size="x-large">mdi-close</v-icon>
        </a>
      </div>

      <div class="profile-edit__form">
        <div class="profile-edit__form__nickname">
          <span>닉네임</span>
          <v-text-field v-model="formData.nickname"
                        single-line
                        density="compact"
                        :rules="[ v => !!v || '닉네임을 입력해주세요.', v => v.length >= 4 || '닉네임을 최소 4자 이상 입력해주세요.' ]" />
        </div>
      </div>
    </div>
  </v-slide-y-reverse-transition>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class ProfileEditPage extends Vue {
  show = false;
  formData = { };

  mounted(): void {
    this.show = true;

    this.formData = {
      nickname: this.$store.state.auth.userBasicInfo?.nickname,
    };
  }
}
</script>

<style lang="scss" scoped>
#profile-edit-page-wrapper {
  pointer-events: initial;
  display: block;
  padding: 2rem;
  margin: 2rem;
  min-width: 50vw;
  border-radius: 1rem;
  background: $color-background;
  box-shadow: 0 0.5rem 1rem rgba(#000, 0.33);

  .profile-edit {
    &__header {
      display: flex;
      justify-content: space-between;
      align-items: center;

      & > :first-child {
        flex-grow: 1;
      }
    }
  }
}
</style>
