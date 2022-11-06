import Stickers from "@/data/items/stickers.json";
import Fonts from "@/data/items/fonts.json";
import Papers from "@/data/items/papers.json";

export interface StoreItemBase {
  name: string,
  price: number,
}

export interface StoreItemStickers extends StoreItemBase {
  assetPath?: string,
}

export interface StoreItemPapers extends StoreItemBase {
  color: string,
}

export interface StoreItemFonts extends StoreItemBase {
  fontFamilyName: string,
}

export interface StoreItemList<T extends StoreItemBase> {
  [id: string]: T,
}

export type ItemType = "stickers" | "fonts" | "papers";

export function getStoreItems(type: ItemType): StoreItemList<StoreItemBase> {
  if(type === "stickers") return Stickers as StoreItemList<StoreItemStickers>;
  else if(type === "fonts") return Fonts as StoreItemList<StoreItemFonts>;
  if(type === "papers") return Papers as StoreItemList<StoreItemPapers>;

  return {};
}
