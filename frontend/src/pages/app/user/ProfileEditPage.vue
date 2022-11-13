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

      <div class="profile-edit__form__age">
        <span>나이대</span>
        <v-select v-model="formData.age"
                  :items="ageItems"
                  item-title="title"
                  item-value="value"
                  single-line
                  density="compact" />
      </div>

      <div class="profile-edit__form__gender">
        <span>성별</span>
        <v-select v-model="formData.gender"
                  :items="genderItems"
                  item-title="title"
                  item-value="value"
                  single-line
                  density="compact" />
      </div>

      <div class="profile-edit__form__job">
        <span>직업</span>
        <v-select v-model="formData.job"
                  :items="jobItems"
                  item-title="title"
                  item-value="value"
                  single-line
                  density="compact" />
      </div>
    </div>

    <div class="profile-edit__controls">
      <button class="button bg-transparent narrow"
              @click="onDeleteAccountButtonClick">계정 삭제</button>

      <div>
        <button class="button"
                @click="$router.back()">닫기</button>
        <button class="button primary"
                @click="onProfileEditButtonClick">수정</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";
import { AGE_ITEMS, GENDER_ITEMS, JOB_ITEMS } from "@/data/profile-data";
import { isSuccessful } from "@/util/backend";

export default class ProfileEditPage extends Vue {
  readonly genderItems = GENDER_ITEMS;
  readonly ageItems = AGE_ITEMS;
  readonly jobItems = JOB_ITEMS;

  formData = { };

  mounted(): void {
    this.formData = {
      nickname: this.$store.state.user.user!.nickname,
      age: this.$store.state.user.user!.profile.age,
      gender: this.$store.state.user.user!.profile.gender,
      job: this.$store.state.user.user!.profile.job,
    };
  }

  onProfileEditButtonClick(): void {
    // TODO
    alert("프로필 수정은 추후 구현 예정입니다.");
  }

  async onDeleteAccountButtonClick() {
    const choice = confirm("계정을 삭제할까요? 계정을 삭제하면 지금까지 보낸 편지 내역과 포인트가 사라져요.");

    if(choice) {
      const prpt = prompt("계정 삭제 확인을 위해 \"계정 삭제\"라고 입력해주세요.");

      if(prpt && prpt === "계정 삭제") {
        const response = await this.$api.deleteUser();

        if(isSuccessful(response.statusCode)) {
          alert("계정을 삭제했어요. 언제든지 다시 가입할 수 있어요.\n\nTo. Anyone을 사용해 주셔서 감사합니다!");
          window.location.href = this.$router.resolve({ name: "logout" }).href;
        } else {
          alert("서버 오류로 계정을 삭제할 수 없었어요. " + response.statusCode);
        }
      } else if(!prpt) {
        alert("계정 삭제를 취소했어요.");
      } else {
        alert("확인 입력을 다르게 입력하셨어요. 계정 삭제를 진행하지 않을게요.");
      }
    }
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

    &__form {
      & > * {
        margin: 1em 0;

        & > span {
          display: inline-block;
          margin-bottom: 0.5em;
        }
      }
    }

    &__controls {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: space-between;
      width: 100%;

      & button {
        margin: 0 0.5em;
      }

      & > button {
        // account delete button
        font-size: 0.8em;
        color: #F47 !important;
      }

      & > div {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-end;
      }
    }
  }
}
</style>
