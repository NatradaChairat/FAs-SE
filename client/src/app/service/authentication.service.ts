import {Injectable} from '@angular/core';
import {AngularFireAuth} from "@angular/fire/auth";
import * as firebase from 'firebase/app';
import '@firebase/auth';
import {HttpClient} from "@angular/common/http";
import {FaceRecognitionService} from "./face-recognition.service";

@Injectable()
export class AuthenticationService {
  private authUrl = 'http://localhost:8080/auth';

  detect: any = {}

  constructor(private angularFireAuthen: AngularFireAuth,
              private http: HttpClient,
              private faceRecognitionService: FaceRecognitionService) {

  }

  loginWithFace(imageUrl: string) {
    return new Promise<any>((resolve, reject) => {
      this.faceRecognitionService.detectImage(imageUrl)
        .subscribe((detectRes: any) => {
          const res: any = detectRes[0];
          const faceId = res.faceId;
          console.log(faceId);
          this.faceRecognitionService.identifyImages(faceId)
            .subscribe((identifyRes) => {
              console.log(identifyRes);
              try {
                const identifyResponse: any = identifyRes[0];
                const candidatesResponse: any = identifyResponse.candidates;
                const confidence = candidatesResponse[0].confidence;
                const personId = candidatesResponse[0].personId;
                if (confidence >= 0.7) {
                  this.faceRecognitionService.getPersonInLargePersonGroup(personId)
                    .subscribe((getPersonRes) => {
                      const nameRes: any =  getPersonRes.name;
                      console.log(nameRes);
                      resolve([nameRes, confidence]);
                    });
                }
              } catch (e) {
                resolve(0);
              }
            }, (error: any) => {
              reject(error);
            });
        }, (error: any) => {
          reject(error);
        });
    });
  }

  login(email: string, password: string) {
    return new Promise<any>((resolve, reject) => {
      firebase.auth().signInWithEmailAndPassword(email, password)
        .then(res => {
          resolve(res);
        }, err => reject(err));
    });
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
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userDetails');
  }

  getCurrentUser() {
    const details = localStorage.getItem('userDetails');
    if (details === null || details.length === 0) {
      return null;
    }
    return JSON.parse(localStorage.getItem('userDetails'));
  }

  hasRole(role: String): boolean {
    const user: any = this.getCurrentUser();
    if (user) {
      const roleList: string[] = role.split(',');
      for (let j = 0; j < roleList.length; j++) {
        const authList = user.authorities;
        const userRole = 'ROLE_' + roleList[j].trim().toUpperCase();
        for (let i = 0; i < authList.length; i++) {
          if (authList[i].name === userRole) {
            return true;
          }
        }
      }
      return false;
    }

  }
}
