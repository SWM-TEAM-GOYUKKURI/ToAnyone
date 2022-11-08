<template>
  <div id="letter-view-wrapper">
    <letter-area v-if="dataLoaded"
                 class="letter-view-area"
                 :senderNickname="letterItem.senderName"
                 :receiverNickname="letterItem.receiverName"
                 :textContent="letterItem.content" />

    <div v-if="dataLoaded && letterItem.replyLetters"
         class="letter-view__replies">
      <letter-area v-for="letter in letterItem.replyLetters"
                   class="letter-view-area reply"
                   :key="letter.id"
                   :senderNickname="letter.senderName"
                   :receiverNickname="letter.receiverName"
                   :textContent="letter.content" />
    </div>

    <a v-if="!lastLetterSentByMe"
       href="#"
       class="letter-view__reply-button button round"
       @click="goLetterReplyPage"><v-icon>mdi-reply</v-icon> <span>답장하기</span></a>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterArea from "@/components/app/letter/LetterArea.vue";
import { isSuccessful } from "@/util/backend";
import { LetterItemFull } from "@/interfaces/backend";

@Options({
  components: {
    LetterArea,
  },
})
export default class LetterViewPage extends Vue {
  letterItem!: LetterItemFull;
  dataLoaded = false;

  get letterId(): number | null {
    if(this.$route.params) {
      return parseInt(this.$route.params.letterId as string);
    } else {
      return null;
    }
  }

  get lastLetterSentByMe(): boolean {
    if(this.dataLoaded && this.letterItem) {
      if(this.letterItem.replyLetters && this.letterItem.replyLetters.length > 0) {
        const lastReplyLetter = this.letterItem.replyLetters[this.letterItem.replyLetters.length - 1];
        if(lastReplyLetter.senderName === this.$store.state.auth.userBasicInfo!.nickname) {
          return true;
        }
      } else {
        if(this.letterItem.senderName === this.$store.state.auth.userBasicInfo!.nickname) {
          return true;
        }
      }
    }

    return false;
  }

  beforeMount(): void {
    if(!this.letterId) {
      alert("편지 정보가 없습니다.");
      this.$router.replace({ name: "main" });
    }
  }

  async mounted() {
    let error = false;

    if(this.letterId) {
      /* Load letter contents */
      const response = await this.$api.getLetter(this.letterId!);

      if(isSuccessful(response.statusCode) && response.data) {
        this.letterItem = response.data;

        this.dataLoaded = true;
      } else {
        // TEMP ALERT
        alert(`편지 데이터 불러오는 중 오류: ${response.statusCode}`);
        error = true;
      }

      /* Letter read state update */
      const readStateUpdateResponse = await this.$api.updateLetterReadState(this.letterId!);

      if(!isSuccessful(readStateUpdateResponse.statusCode)) {
        // TEMP ALERT
        alert(`편지 읽기 상태 업데이트 중 오류: ${readStateUpdateResponse.statusCode}`);
        error = true;
      }

      /* Update global unread letters */
      // Load unread letters and save it
      if(!(await this.$api.updateUnreadLetters(this))) {
        // TEMP ALERT
        alert("편지 보관 목록을 업데이트하는 중 오류");
      }
    }

    if(error) {
      this.$router.back();
    }
  }

  goLetterReplyPage(): void {
    this.$router.push({
      name: "letter-reply",
      params: {
        replyMode: "true",
        replyModeData: JSON.stringify({ ...this.letterItem, id: this.letterId }),
      },
    });
  }
}
</script>

<style lang="scss" scoped>
#letter-view-wrapper {
  padding: 1rem 0;

  .letter-view-area {
    width: 80vw;
    min-height: 50vh;
    margin: 3rem auto;

    &.reply {
      width: 70vw;
    }
  }

  .letter-view {
    &__reply-button {
      position: fixed;
      bottom: 2rem;
      right: 2rem;
      font-size: 1.25em;
      box-shadow: 0 0.25em 0.75em rgba(0, 0, 0, 0.5);
      white-space: pre;
    }
  }
}
</style>
