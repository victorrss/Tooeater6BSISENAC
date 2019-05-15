export class UserModel {
    public id: number;
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

    public acceptPolicy: boolean; // only FRONT END
}