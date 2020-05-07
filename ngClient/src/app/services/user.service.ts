import {Injectable} from '@angular/core';
import {UserWebService} from './web/user-web.service';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userMap: Map<number, User> = new Map();
  private registered: number[] = [];

  constructor(private staffWebService: UserWebService) {
  }

  public loadStaff(ids: number[]) {
    const toLoad = [];
    ids.forEach(id => {
      if (!this.userMap.has(id) && this.registered.findIndex(val => val === id) === -1) {
        toLoad.push(id);
      }
    });
    if (toLoad.length) {
      this.registered.push(...toLoad);
      this.staffWebService.getByIds(toLoad).subscribe(staff => {
        staff.forEach(val => this.userMap.set(val.id, val));
      });
    }
  }

  public getById(id: number) {
    let result = this.userMap.get(id);

    if (result === undefined) {
      this.loadStaff([id]);
      result = this.userMap.get(id);
    }
    return result;
  }
}
