<template>
  <div id="letter-box-wrapper">
    <letter-box-item v-for="item in letterItems"
                     :key="item.letterId"
                     :letterItem="item" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterBoxItem from "@/components/app/letter/LetterBoxItem.vue";
import { ILetterItemPreview } from "@/interfaces/ILetterItem";

@Options({
  components: {
    LetterBoxItem,
  },
})
export default class LetterBoxPage extends Vue {
  testLetterItems: ILetterItemPreview[] = [{
    letterId: 999999,
    sender: {
      nickname: "테스트 발신자 1",
      profileImageId: 30000,
    },
    sentAt: new Date(),
    readBefore: true,
    previewTextContent: "간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 공 공장장이다.",
  }, {
    letterId: 999998,
    sender: {
      nickname: "테스트 발신자 2",
      profileImageId: 30001,
    },
    sentAt: new Date(),
    readBefore: false,
    previewTextContent: "내가 그린 기린 그림은 목이 긴 기린 그린 그림이고, 네가 그린 기린 그림은 목이 안 긴 기린 그린 그림이다.",
  }];

  get letterItems(): ILetterItemPreview[] {
    return Array.from(this.testLetterItems).sort((a, b) => {
      // TODO: should test this sort condition is reliable

      if(a.readBefore === b.readBefore) {
        if(a.sentAt > b.sentAt) return 1;
        if(a.sentAt < b.sentAt) return -1;
      } else {
        return a.readBefore ? 1 : -1;
      }

      return 0;
    });
  }

  onLetterBoxItemClick(): void {
    this.$router.push({ name: "letter-view", params: { letterId: 9999999 } });
  }
}
</script>

<style lang="scss" scoped>
#letter-box-wrapper {
  display: flex;
  flex-direction: column;
  padding: 2em 0;
  width: 75vw;
  margin: auto;
}
</style>
