import {Injectable} from '@angular/core';
import {Account} from '../model/Account.model';

@Injectable()
export class IntermediaryService {

  private account: Account;

  private confidence: number;

  private phoneNumber: string;

  constructor() {
  }

  setPhoneNumber(phoneNumber: string) {
    this.phoneNumber = phoneNumber;
  }

  getPhoneNumber(): string {
    return this.phoneNumber;
  }

  setConfidence(confidence: number) {
    this.confidence = confidence;
  }

  getConfidence(): number {
    return this.confidence;
  }

  setAccount(account: Account) {
    this.account = account;
  }

  getAccount(): Account {
    return this.account;
  }

}
