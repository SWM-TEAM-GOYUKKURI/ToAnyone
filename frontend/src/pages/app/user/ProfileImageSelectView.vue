<template>
  <div id="profile-edit-image-wrapper">
    <div class="profile-edit-image__container">
      <div v-for="itemId in itemIdList"
           :key="itemId"
           class="item"
           :class="{ selected: selectedItemId === itemId }"
           data-id="itemId"
           @click="selectedItemId = itemId">
        <profile-image :srcUrl="getPicsumUrl(itemId)" />
        <div class="check"><v-icon>mdi-check</v-icon></div>
      </div>
    </div>

    <span style="display: inline-block; font-size: 0.8em; margin: 0.5em 0;">※ <a href="https://picsum.photos" target="_blank">picsum.photos</a>에서 제공하는 이미지를 사용합니다.</span>

    <div class="profile-edit-image__controls">
      <button class="button narrow"
              @click="idBase = Math.floor(Math.random() * 9)"><v-icon>mdi-refresh</v-icon> <span>돌려돌려~</span></button>

      <div>
        <button class="button"
                @click="$router.back()">닫기</button>
        <button class="button primary"
                @click="onConfirmClick">선택</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import ProfileImage from "@/components/app/global/ProfileImage.vue";
import { getPicsumUrl } from "@/util/path-transform";
import { isSuccessful } from "@/util/backend";

@Options({
  components: {
    ProfileImage,
  },
})
export default class ProfileImageSelectView extends Vue {
  getPicsumUrl = getPicsumUrl;

  idBase = 1;

  selectedItemId = 0;

  get itemIdList(): number[] {
    const list = [0];
    const idBase2 = this.idBase + 10;
    for(let i = idBase2; i <= idBase2 * 30; i += idBase2) list.push(i);
    return list;
  }

  mounted(): void {
    const tmp = parseInt(this.$store.state.user.user!.userImageUrl);
    this.selectedItemId = !tmp ? 0 : tmp;
  }

  async onConfirmClick() {
    const response = await this.$api.updateUserProfileImage(this.selectedItemId);

    if(isSuccessful(response.statusCode)) {
      alert("프로필 이미지 설정 완료!");
    } else {
      alert("프로필 이미지를 업데이트할 수 없습니다. " + response.statusCode);
    }

    /* Update user */
    const userResponse = await this.$api.getUserInfo();

    if(isSuccessful(userResponse.statusCode)) {
      this.$store.commit("user/updateUserInfo", userResponse.data);
      this.$emit("updateProfileDataRequest");
      this.$router.back();
    } else {
      alert("사용자 정보를 가져올 수 없습니다. " + userResponse.statusCode);
    }
  }
}
</script>

<style lang="scss">
#profile-edit-image-wrapper {
  max-width: 80vw;
  max-height: 80vh;
  overflow: hidden;

  a { text-decoration: underline; }

  .profile-edit-image {
    &__container {
      max-height: 60vh;
      overflow-y: auto;
      display: flex;
      justify-content: center;
      flex-direction: row;
      flex-wrap: wrap;

      & > .item {
        position: relative;
        margin: 1em;

        & > span {
          display: inline-block;
          text-align: center;
          margin-top: 0.25em;
        }

        .check {
          position: absolute;
          right: -0.5em;
          bottom: -0.5em;
          display: none;
          padding: 0.33em;
          background-color: $color-primary;
          color: $color-dark;
          border-radius: 999999rem;
        }

        &.selected {
          .check {
            display: inline-block;
          }
        }
      }
    }

    &__controls {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-top: 1em;

      & > div {
        display: flex;
        align-items: center;
        justify-content: flex-end;

        & > * {
          margin: 0 0.5em;
        }
      }
    }
  }
}
</style>
