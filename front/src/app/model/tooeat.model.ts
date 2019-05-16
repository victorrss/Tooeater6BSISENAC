import { UserModel } from "./user.model";
import { CommentModel } from "./comment.model";

export class TooeatModel {
    public id: number;
    public user: UserModel;
    public comments: number;
    public likes: number;
    public text: string;
    public media: string;
    public createdAt: Date;
    public updateAt: Date;

    public commentsObj: CommentModel[]
}