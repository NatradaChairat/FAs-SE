import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Account} from "../entity/Account";
import {Observable} from "rxjs/internal/Observable";
import {catchError} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders(
    { 'Content-Type': 'application/json' ,
      'Cache-Control': 'no-cache',
      'Access-Control-Allow-Origin': 'http://localhost:4200',
      'Access-Control-Allow-Methods': "POST, GET, OPTIONS",
      'Access-Control-Allow-Headers': "*"

    })
};

@Injectable(
  {
    providedIn: 'root'
  }
)
export class AccountDataServerService{
 /* private accountUrl = 'api/account';
  private headers = new Headers({'Content-Type': 'application/json'});
  */

 private baseUrl = 'http://localhost:8080/account';
  //private baseUrl = '/api';
  constructor(private http: HttpClient){}

  sendAccount(account:Account): Observable<Object>{
    console.log("Send Account .."+account.email+ " to Url: "+this.baseUrl);
    /*let httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json' , 'Access-Control-Allow-Origin': '*'})};
    let body = JSON.stringify(account);
    return this.http.post<Account>('http://localhost:8080/account',body,httpOptions);
*/
    return this.http.post(`${this.baseUrl}`+`/create`, account,httpOptions);

  }

  getAccountByUsername(username: String){
    console.log("Get Account ..");
    return this.http.get(`${this.baseUrl}`+ `/get/`+username,httpOptions);
  }
}
