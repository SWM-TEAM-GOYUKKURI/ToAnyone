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
       class="letter-view__reply-button animation-button"
       @click="goLetterReplyPage"><v-icon>mdi-reply</v-icon> 답장하기</a>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterArea from "@/components/app/letter/LetterArea.vue";
import { beGET } from "@/util/backend";
import { LetterItemFull } from "@/interfaces/backend";

@Options({
  components: {
    LetterArea,
  },
})
export default class LetterViewPage extends Vue {
  letterItem!: LetterItemFull;
  dataLoaded = false;

  get letterId(): string | null {
    if(this.$route.params) {
      return this.$route.params.letterId as string;
    } else {
      return "";
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
      this.$router.replace({ name: "main" });
    }
  }

  async mounted() {
    const response = await beGET<LetterItemFull>(`/letter/inbox/${this.$route.params.letterId}`, null, { credentials: this.$store.state.auth.token! });

    if(response.statusCode === 200 && response.data) {
      this.letterItem = response.data;

      this.dataLoaded = true;
    } else {
      // Error handling
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
      display: flex;
      align-items: center;
      justify-content: center;
      position: fixed;
      bottom: 2rem;
      right: 2rem;
      padding: 1em 1.5em;
      font-size: 1.25em;
      background-color: $color-secondary;
      box-shadow: 0 0.25em 0.75em rgba(0, 0, 0, 0.5);
      border-radius: 999999rem;
      white-space: pre;
    }
  }
}
</style>
