<template>
  <div id="letter-box-wrapper">
    <v-progress-circular v-if="!requestCompleted"
                         indeterminate />

    <div v-if="requestCompleted && letterItems.length > 0">
      <div class="letter-box__total">📫 총 <span class="t-primary" style="font-weight: bold">{{ letterItems.length }}</span>통의 편지가 있어요.</div>

      <div class="letter-box__items">
        <letter-box-item v-for="item in letterItems"
                          :key="item.letterId"
                          :letterItem="item" />
      </div>
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

  .letter-box {
    &__total {
      font-size: 1.5em;
      text-align: left;
    }

    &__items {
      margin-top: 1.5em;
    }
  }

  .no-letters {
    font-size: 2em;
  }
}
</style>
