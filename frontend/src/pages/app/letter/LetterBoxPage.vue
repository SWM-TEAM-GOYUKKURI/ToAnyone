<template>
  <div id="letter-box-wrapper">
    <v-progress-circular v-if="!requestCompleted"
                         indeterminate />

    <div v-if="requestCompleted && letterItems.length > 0">
      <div class="letter-box__total">π« μ΄ <span class="t-primary" style="font-weight: bold">{{ letterItems.length }}</span>ν΅μ νΈμ§κ° μμ΄μ.</div>

      <div class="letter-box__items">
        <letter-box-item v-for="item in letterItems"
                         :key="item.letterId"
                         :letterItem="item"
                         :sentByMe="_sentLetterIds.includes(item.id)" />
      </div>
    </div>
    <div v-else-if="requestCompleted && letterItems.length <= 0">
      <span class="no-letters">μμ§ λ°μ νΈμ§κ° μμ΄μπ</span>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterBoxItem from "@/components/app/letter/LetterBoxItem.vue";
import { filterUnreadLetters, isSuccessful } from "@/util/backend";
import { LetterInboxItemList } from "@/interfaces/backend";

@Options({
  components: {
    LetterBoxItem,
  },
})
export default class LetterBoxPage extends Vue {
  _letterItems: LetterInboxItemList = [];
  _sentLetterIds: number[] = [];
  requestCompleted = false;

  get letterItems(): LetterInboxItemList {
    return Array.from(this._letterItems).sort((a, b) => {
      if(a.read && !b.read) return 1;
      else if(a.read && b.read) return 0;
      else if(!a.read && b.read) return -1;

      return 0;
    });
  }

  async mounted() {
    await this.loadInbox();
    this.requestCompleted = true;
  }

  async loadInbox() {
    /* === Inbox & Sent Letters Inbox === */
    // Since some special procedures should be happen in this, just don't use `this.$api.updateUnreadLetters()`.

    const response = await this.$api.getInbox();
    const sentLettersResponse = await this.$api.getSentInbox();

    if(isSuccessful(response.statusCode) && isSuccessful(sentLettersResponse.statusCode)) {
      if(response.data && sentLettersResponse.data) {
        this._letterItems = [...response.data, ...sentLettersResponse.data].sort((a, b) => new Date(b.sendDate).getTime() - new Date(a.sendDate).getTime());
        this._sentLetterIds = sentLettersResponse.data.map((v) => v.id);

        /* === Save unread letters to store === */
        this.$store.commit("user/updateUnreadLetters", filterUnreadLetters(response.data));
        this.$store.commit("user/updateUnreadSentLetters", filterUnreadLetters(sentLettersResponse.data));
      } else {
        this._letterItems = [];
      }
    } else {
      // TEMP ALERT
      alert(`νΈμ§ λ³΄κ΄ λͺ©λ‘ λ° μ μ‘ν νΈμ§ λͺ©λ‘μ λΆλ¬μ€λ μ€ μ€λ₯: ${response.statusCode}, ${sentLettersResponse.statusCode}`);
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

  @media (max-width: $viewport-small-max-width) {
    width: 90vw;
    font-size: 0.9em;
  }

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
