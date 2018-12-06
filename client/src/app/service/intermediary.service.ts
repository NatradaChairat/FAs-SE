import {Injectable} from '@angular/core';
import {Account} from '../model/Account.model';

@Injectable()
export class IntermediaryService {

  private account: Account;

  constructor() {
  }

  setAccount(account: Account) {
    this.account = account;
  }

  getAccount(): Account {
    return this.account;
  }

}
