import { UserModel } from "./user.model";

export class TooeatModel {
    public id: number;
    public user: UserModel;
    public comments: number;
    public likes: number;
    public text: string;
    public media: string;
    public createdAt: Date;
    public updateAt: Date;
}