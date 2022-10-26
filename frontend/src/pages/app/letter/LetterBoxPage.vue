<template>
  <div id="letter-box-wrapper">
    <!-- <div v-if="devmode" @click="testlettertome">테스트 편지 받기</div> -->

    <v-progress-circular v-if="!requestCompleted"
                        indeterminate />

    <div v-if="requestCompleted && letterItems.length > 0">
      <letter-box-item v-for="item in letterItems"
                        :key="item.letterId"
                        :letterItem="item" />
    </div>
    <div v-else-if="requestCompleted && letterItems.length <= 0">
      <span class="no-letters">아직 받은 편지가 없어요😖</span>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterBoxItem from "@/components/app/letter/LetterBoxItem.vue";
import { beGET /* , bePOST */ } from "@/util/backend";
import { LetterInboxItemList } from "@/interfaces/backend";

@Options({
  components: {
    LetterBoxItem,
  },
})
export default class LetterBoxPage extends Vue {
  _letterItems: LetterInboxItemList = [];
  requestCompleted = false;

  get devmode(): boolean { return process.env.VUE_APP_DEVMODE === "true"; }

  get letterItems(): LetterInboxItemList {
    return Array.from(this._letterItems).sort((a, b) => {
      if(!a.read) return -1;

      // TODO: sort by read status, receive date...

      return 0;
    });
  }

  async mounted() {
    await this.loadInbox();
    this.requestCompleted = true;
  }

  async loadInbox() {
    const response = await beGET<LetterInboxItemList>("/letter/inbox", null, { credentials: this.$store.state.auth.token! });

    if(response.statusCode === 200) {
      if(response.data) {
        this._letterItems = response.data;
      } else {
        this._letterItems = [];
      }
    } else {
      // TEMP ALERT
      alert(`편지 보관 목록 불러오는 중 오류: ${response.statusCode}`);
    }
  }

  /* async testlettertome() {
    const response = await bePOST("/dev/letter", {
      content: "테스트 편지",
      senderEmail: this.$store.state.auth.userBasicInfo?.email,
      receiverEmail: this.$store.state.auth.userBasicInfo?.email,
    }, {
      credentials: this.$store.state.auth.token!,
    });

    if(response.statusCode === 201) {
      // HTTP 201 Created: Letter sent successfully
      await this.loadInbox();
    } else {
      // Error handling
    }
  } */
}
</script>

<style lang="scss" scoped>
#letter-box-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2em 0;
  width: 75vw;
  margin: auto;

  & > * {
    width: 100%;
    text-align: center;
  }

  .no-letters {
    font-size: 2em;
  }
}
</style>
