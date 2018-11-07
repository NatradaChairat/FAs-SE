import {Injectable} from "@angular/core";
import * as firebase from 'firebase/app';
import '@firebase/storage';

@Injectable()
export class FirebaseService {

  constructor(){ }

  saveImageToStorage(imageUpload: string, childPath: string){
    return new Promise<any>((resolve, reject) => {
      firebase.storage().ref().child(childPath).putString(imageUpload, 'data_url')
        .then(res => {
          this.getImageUrl(childPath)
          resolve(res)
        }, err => reject(err))
    })

  }

  getImageUrl(childPath: string){
    firebase.storage().ref().child(childPath).getDownloadURL()
      .then(res => {
        console.log(res)
      }, err => console.log(err))


    return
  }

}
