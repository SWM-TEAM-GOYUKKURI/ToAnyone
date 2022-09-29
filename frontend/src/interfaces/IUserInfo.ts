export interface IUserBasicInfo {
  nickname: string,
  signedUp: boolean,
  // TODO: more items to be added
}

export interface IUserInfo extends IUserBasicInfo {
  achievements: number[],
  // TODO: more items to be added
}
