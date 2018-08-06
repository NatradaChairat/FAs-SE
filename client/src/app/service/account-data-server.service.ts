import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {throwError} from "rxjs/internal/observable/throwError";
import {Account} from "../entity/Account";
import {catchError, tap} from "rxjs/operators";
import {any} from "codelyzer/util/function";

const httpOptions = {
  headers: new HttpHeaders(
    { 'Content-Type': 'application/json ; charset=utf-8; application/x-www-form-urlencoded ; text/plain',
      'Cache-Control': 'no-cache',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': "GET, POST, PATCH, PUT, DELETE, OPTIONS, REQUEST",
      'Access-Control-Allow-Headers': "Origin, Content-Type, X-Auth-Token",
      'responseType': "text",

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

  sendAccount(account:Account): Observable<Object> {
    return this.http.post(`${this.baseUrl}`+`/create`, account,{ observe: 'response',responseType: 'text' });
  }

  sendPersonalAccount(account: Account, refParam: string): Observable<Object>{
    console.log(account);
    return this.http.post(`${this.baseUrl}`+`/update/`+encodeURIComponent(refParam),account,{ observe: 'response'});
  }

  sendEmail(param: string){
    return this.http.get(`${this.baseUrl}`+`/send/email/`+encodeURIComponent(param),httpOptions);
  }

  updateStatusByParam(id: string,token: string){
    console.log("Get param: "+encodeURIComponent(id)+" "+encodeURIComponent(token));
    return this.http.get(`${this.baseUrl}`+`/get/status/?id=`+encodeURIComponent(id)+"&time="+encodeURIComponent(token));

  }

  checkDuplicatedStudentId(studentId: string){
    return this.http.get(`${this.baseUrl}`+ `/get/studentId/`+studentId);
  }

  checkDuplicatedPhonenumber(phonenumber: string){
    return this.http.get(`${this.baseUrl}`+ `/get/phonenumber/`+phonenumber);
  }

  getVerifyPhonenumberCode(param: string){
    return this.http.get(`${this.baseUrl}`+`/send/phonenumber/`+encodeURIComponent(param),{observe: 'response'});
  }



  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  };




}
