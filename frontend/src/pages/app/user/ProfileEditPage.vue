<template>
  <div id="profile-edit-page-wrapper">
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

    <div class="profile-edit__controls">
      <button class="button"
              @click="$router.back()">닫기</button>
      <button class="button primary"
              @click="onProfileEditButtonClick">수정</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class ProfileEditPage extends Vue {
  formData = { };

  mounted(): void {
    this.formData = {
      nickname: this.$store.state.auth.userBasicInfo?.nickname,
    };
  }

  onProfileEditButtonClick(): void {
    // TODO
  }
}
</script>

<style lang="scss" scoped>
#profile-edit-page-wrapper {
  padding: 0 1em;
  width: 400px;

  @media (max-width: $viewport-small-max-width) {
    width: 100%;
    height: 100%;
    padding: 1em;
  }

  .profile-edit {
    &__header {
      display: flex;
      justify-content: space-between;
      align-items: center;

      & > :first-child {
        flex-grow: 1;
      }
    }

    &__controls {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: flex-end;
      width: 100%;

      & > button {
        margin: 0 0.5em;
      }
    }
  }
}
</style>
