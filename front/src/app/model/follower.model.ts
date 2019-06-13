import { UserModel } from './user.model';

export class FollowerModel {
  public id: number;
  public userSlave: UserModel;
  public userMaster: UserModel;
  public enabled: boolean;
}
