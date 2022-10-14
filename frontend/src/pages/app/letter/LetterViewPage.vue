<template>
  <div id="letter-view-wrapper">
    <letter-area v-if="dataLoaded"
                 class="letter-view-area"
                 :senderNickname="letterItem.senderName"
                 :receiverNickname="letterItem.receiverName"
                 :textContent="letterItem.content" />

    <a href="#" @click="goLetterReplyPage">답장</a>
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
  padding: 3rem 0;

  .letter-view-area {
    width: 80vw;
    min-height: 70vh;
    margin: auto;
  }
}
</style>
