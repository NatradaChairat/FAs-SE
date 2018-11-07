import {Injectable} from '@angular/core';
import {AngularFireAuth} from "@angular/fire/auth";
import * as firebase from 'firebase/app'
import '@firebase/auth';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class AuthenticationService {
  private authUrl = 'http://localhost:8080/auth';

  constructor( private angularFireAuthen: AngularFireAuth, private http: HttpClient) {

  }

  loginWithFace(imageUrl: string){
    console.log(imageUrl)
    return this.http.post(`${this.authUrl}`+`/faceLogin`,imageUrl);

  }

  login(email: string, password: string){
    return new Promise<any>((resolve, reject) => {
      firebase.auth().signInWithEmailAndPassword(email, password)
        .then(res => {
          resolve(res);
        }, err => reject(err))
    })
    /*return this.http.post(this.authUrl, JSON.stringify({
      username: username,
      password: password
    }), {headers: this.headers})
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let token = response.json() && (response.json() as any).token;
        if (token) {
          // store username and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify({username: username, token: token}));
          let student = response.json().account;
          console.log(student);
          localStorage.setItem('userDetails', JSON.stringify(student));
          // return true to indicate successful login
          return true;
        } else {
          // return false to indicate failed login
          return false;
        }
      }).catch((error: any) => Observable.throw(error.json().error || 'Server error'));*/
  }

  getToken(): string {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var token = currentUser && currentUser.token;
    return token ? token : '';
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userDetails');
  }

  getCurrentUser() {
    let details = localStorage.getItem('userDetails');
    if (details == null || details.length == 0) {
      return null;
    }
    return JSON.parse(localStorage.getItem('userDetails'));
  }

  hasRole(role: String): boolean {
    let user: any = this.getCurrentUser();
    if (user) {
      let roleList: string[] = role.split(',');
      for (let j = 0; j < roleList.length; j++) {
        let authList = user.authorities;
        let userRole = 'ROLE_' + roleList[j].trim().toUpperCase();
        for (let i = 0; i < authList.length; i++) {
          if (authList[i].name == userRole) {
            return true;
          }
        }
      }
      return false;
    }

  }
}
