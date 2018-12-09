import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {throwError} from "rxjs/internal/observable/throwError";
import {Account} from "../model/Account.model";

const httpOptions = {
  headers: new HttpHeaders(
    {
      'Content-Type': 'application/json ; charset=utf-8; application/x-www-form-urlencoded ; text/plain',
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
export class AccountDataServerService {
  /* private accountUrl = 'api/account';
   private headers = new Headers({'Content-Type': 'application/json'});
   */

  private baseUrl = 'http://localhost:8080/account';

  constructor(private http: HttpClient) {
  }

  sendAccount(account: Account): Observable<Object> {
    return this.http.post(`${this.baseUrl}` + `/create`, account, {observe: 'response', responseType: 'text'});
  }

  sendPersonalAccount(account: Account, refParam: string): Observable<Object> {
    account.uid = refParam;
    return this.http.post(`${this.baseUrl}` + `/update/`/*+encodeURIComponent(refParam)*/, account, {observe: 'response'});
  }

  getAccountByParam(param: string) {
    return this.http.get(`${this.baseUrl}` + `/get/` + encodeURIComponent(param), httpOptions);
  }

  getAccountByStatus(status: String) {
    return this.http.get(`${this.baseUrl}` + `/get/account/` + status, httpOptions);
  }

  sendEmail(param: string) {
    return this.http.get(`${this.baseUrl}` + `/send/email/` + encodeURIComponent(param), httpOptions);
  }

  sendResultAuthenProcessToEmail(param: string, status: string, reason: string) {
    return this.http.get(`${this.baseUrl}` + `/send/email/` +
      encodeURIComponent(param) + `/` + encodeURIComponent(status) + `/` + reason, httpOptions);
  }


  updateStatusByParam(id: string, token: string) {
    // console.log("Get param: "+encodeURIComponent(id)+" "+encodeURIComponent(token));
    return this.http.get(`${this.baseUrl}` + `/update/status/?id=` + encodeURIComponent(id) + `&time=` + encodeURIComponent(token));
  }

  updateStatusByVerifyPhone(id: string) {
    return this.http.get(`${this.baseUrl}` + `/update/status/` + encodeURIComponent(id));
  }

  updateStatus(account: Account): Observable<Object> {
    return this.http.post(`${this.baseUrl}` + `/update/status/`, account);
  }

  checkDuplicatedStudentId(studentId: string) {
    return this.http.get(`${this.baseUrl}` + `/get/studentId/` + studentId);
  }

  checkDuplicatedPhonenumber(phonenumber: string) {
    return this.http.get(`${this.baseUrl}` + `/get/phonenumber/` + phonenumber);
  }

  getVerifyPhonenumberCode(phonenumber: string) {
    return this.http.get(`${this.baseUrl}` + `/send/phonenumber/` + phonenumber, {observe: 'response'});
  }

  saveReasonByParam(reason: string, param: string) {
    return this.http.post(`${this.baseUrl}` + `/reason/` + encodeURIComponent(param) + `/` + reason, httpOptions);
  }

  getReasonByParam(param: string) {
    return this.http.get(`${this.baseUrl}` + `/reason/` + encodeURIComponent(param));
  }


}
