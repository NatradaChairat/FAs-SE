import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Account} from "../entity/Account";
import {Observable} from "rxjs/internal/Observable";
import {catchError, map, tap} from "rxjs/operators";
import {throwError} from "rxjs/internal/observable/throwError";

const httpOptions = {
  headers: new HttpHeaders(
    { 'Content-Type': 'application/json ; charset=utf-8; text/plain',
      'Cache-Control': 'no-cache',
      'Access-Control-Allow-Origin': 'http://localhost:4200',
      'Access-Control-Allow-Methods': "POST, GET, OPTIONS",
      'Access-Control-Allow-Headers': "*",

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
    return this.http.post<Account>('http://localhost:8080/account',body,httpOptions);*/
    return this.http.post(`${this.baseUrl}`+`/create`, account,httpOptions);
  }

  getAccountByUsername(username: String){
    console.log("Username: Request Account .. "+username);
    return this.http.get(`${this.baseUrl}`+ `/get/username/`+username, {responseType: 'text'})
      .pipe(tap((res: any) =>{
        if(res){
          if(res.status == 200){
            return [{ status: res.status, json: res }]
          }else if(res.status ==204){
            return [{ status: res.status, json: res }]
          }
        }
      })
      );
  }

  getAccountByEmail(email: String){
    console.log("Email: Request Account .. "+email);
    return this.http.get(`${this.baseUrl}`+ `/get/email/`+email, {responseType: 'text'})
      .pipe(tap((res: any) =>{
          if(res){
            if(res.status == 200){
              return [{ status: res.status, json: res }]
            }else if(res.status ==204){
              return [{ status: res.status, json: res }]
            }
          }
        })
      );
  }


}
