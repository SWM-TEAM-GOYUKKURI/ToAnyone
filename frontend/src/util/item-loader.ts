import Stickers from "@/data/items/stickers.json";
import Fonts from "@/data/items/fonts.json";
import Papers from "@/data/items/papers.json";

export interface StoreItem {
  name: string,
  price: number,
}

export interface StoreItemList {
  [id: string]: StoreItem,
}

export type ItemType = "stickers" | "fonts" | "papers";

export function getStoreItems(type: ItemType): StoreItemList {
  if(type === "stickers") return Stickers as StoreItemList;
  else if(type === "fonts") return Fonts as StoreItemList;
  else if(type === "papers") return Papers as StoreItemList;

  return {};
}
