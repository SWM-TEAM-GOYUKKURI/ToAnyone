export interface IUserBasicInfo {
  nickname: string,
  email: string,
  // TODO: more items to be added
}

export interface IUserInfo extends IUserBasicInfo {
  achievements: number[],
  // TODO: more items to be added
}
