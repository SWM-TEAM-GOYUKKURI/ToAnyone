import Stickers from "@/data/json/items/stickers.json";
import Fonts from "@/data/json/items/fonts.json";
import Papers from "@/data/json/items/papers.json";

export interface StoreItemBase {
  name: string,
  price: number,
  default?: boolean,
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

export function getDefaultItems(type: ItemType): StoreItemList<StoreItemBase> {
  const items = getStoreItems(type);
  const returnValue: StoreItemList<StoreItemBase> = {};

  for(const itemIndex in items) {
    if(items[itemIndex].default) {
      returnValue[itemIndex] = items[itemIndex];
    }
  }

  return returnValue;
}
