export interface UserInfoBasic {
  nickname: string,
  email: string,
  firstSignupPassed: boolean,
}

export interface UserInfo extends UserInfoBasic {
  profileImageId: number,
}
