export class UserModel {
  public id: number = 0;
  public tooeats: number;
  public following: number;
  public followers: number;
  public firstName: string;
  public lastName: string;
  public photo: string;
  public nickname: string;
  public birthday: Date;
  public gender: boolean;
  public bio: string;
  public email: string;
  public createdAt: Date;
  public updateAt: Date;
  public password: string;

  // artificial
  public acceptPolicy: boolean; // only FRONT END
  public isFollower: number = null; // NULL - AGUARDANDO HTTP RESPONSE; 1 - NAO SEGUE; 2 - AGUARDANDO INVITE; 3 - ESTÁ SEGUINDO
}
