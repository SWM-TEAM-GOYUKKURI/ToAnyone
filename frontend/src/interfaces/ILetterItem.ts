export interface ILetterItemPreview {
  letterId: number,
  sender: {
    nickname: string,
    profileImageId: number,
  },
  sentAt: Date,
  readBefore: boolean,
  previewTextContent?: string,
}

export interface ILetterItem extends ILetterItemPreview {
  textContent: string,
}
