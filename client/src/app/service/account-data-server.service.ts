import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Account} from "../entity/Account";
import {Observable} from "rxjs/internal/Observable";
import {catchError, map, tap} from "rxjs/operators";
import {throwError} from "rxjs/internal/observable/throwError";

const httpOptions = {
  headers: new HttpHeaders(
    { 'Content-Type': 'application/json ; charset=utf-8; text/plain ',
      'Cache-Control': 'no-cache',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': "GET, POST, PATCH, PUT, DELETE, OPTIONS",
      'Access-Control-Allow-Headers': "Origin, Content-Type, X-Auth-Token",

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
    return this.http.post(`${this.baseUrl}`+`/create`, account,{responseType: 'text'});
  }

  getAccountByUsername(username: String): Observable<Object>{
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

  getAccountByEmail(email: String): Observable<Object>{
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

  getAccountByParam(param: string, localTime: string){
    console.log("Get param: "+encodeURIComponent(param)+"/"+encodeURIComponent(localTime));
    return this.http.get(`${this.baseUrl}`+`/get/status/`+encodeURIComponent(param)+`/`+localTime/*encodeURIComponent(localTime)*/,httpOptions)
      ;

  }


  updateStatusByEmailUsername(email:string, username: string, localtime:string): Observable<Object>{
    console.log("AccountId: Request status .. "+email+" "+username+ " "+localtime);
    return this.http.get(`${this.baseUrl}`+ `/get/status/`+email+`/`+username+`/`+localtime, {responseType: 'text'})
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

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  };




}
