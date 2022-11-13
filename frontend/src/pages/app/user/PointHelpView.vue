<template>
  <div style="line-height: 2">
    <p>포인트는 아이템 상점에서 아이템을 구매할 때 사용할 수 있어요.</p>
    <p>포인트는 로그인, 편지 쓰기, 업적 달성 등으로 얻을 수 있어요.</p>
    <p>포인트는 실제 재화로 구매하거나 전환할 수 없어요.</p>

    <div class="point-help__controls">
      <button class="button"
              @click="$router.back()">닫기</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class PointHelpView extends Vue { }
</script>

<style lang="scss" scoped>
.point-help {
  &__controls {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-end;
    width: 100%;
    margin-top: 1em;

    & > button {
      margin: 0 0.5em;
    }
  }
}
</style>
