import { UserModel } from "./user.model";

export class CommentModel {
    public id: number;
    public user: UserModel;
    public text: string;
    public createdAt: Date;
}