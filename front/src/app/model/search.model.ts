import { UserModel } from 'src/app/model/user.model';
import { TooeatModel } from './tooeat.model';
export class SearchModel {
  public user: UserModel;
  public tooeats: TooeatModel[] = [];
}
