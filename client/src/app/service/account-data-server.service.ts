import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Account} from "../model/Account";

@Injectable()
export class AccountDataServerService{
  constructor(private http: HttpClient){}

  sendAccount(account:Account){
    let httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json' })};
    let body = JSON.stringify(account);
    return this.http.post('http://localhost:8080/account',body,httpOptions);
  }
}
