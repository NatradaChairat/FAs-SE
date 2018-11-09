import {Injectable} from "@angular/core";
import * as firebase from 'firebase/app';
import '@firebase/storage';
import {formatDate} from "@angular/common";
import {reject} from "q";

@Injectable()
export class FirebaseService {

  constructor() { }

  saveImageToStorage(imageUpload: string, fullPath: string) {
    return new Promise<any>((resolve, reject) => {
      firebase.storage().ref().child(fullPath).putString(imageUpload, 'data_url')
        .then(res => {
          resolve(res);
        }, err => reject(err));
    });

  }

  getImageUrl(childPath: string) {
    return new Promise<any> ((resolve, reject) => {
      firebase.storage().ref().child(childPath).getDownloadURL()
      .then(res => {
        resolve(res);
      }, err => reject(err));
    });
  }

}
