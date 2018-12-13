import {Injectable} from '@angular/core';
import * as firebase from 'firebase/app';
import '@firebase/storage';
import {environment} from '../../environments/environment';

@Injectable()
export class FirebaseService {

  constructor() {
    if (!firebase.apps.length) {
      firebase.initializeApp(environment.firebase);
    }
  }

  saveImageToStorage(imageUpload: string, fullPath: string) {
    return new Promise<any>((resolve, reject) => {
      firebase.storage().ref().child(fullPath).putString(imageUpload, 'data_url')
        .then(res => {
          resolve(res);
        }, err => reject(err));
    });

  }

  getImageUrl(childPath: string) {
    return new Promise<any>((resolve, reject) => {
      firebase.storage().ref().child(childPath).getDownloadURL()
        .then(res => {
          resolve(res);
        }, err => reject(err));
    });
  }

}
