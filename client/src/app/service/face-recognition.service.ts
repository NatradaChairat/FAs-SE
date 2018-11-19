import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable()
export class FaceRecognitionService {

  subscriptionKey = 'd8572e76b66046b9ac4113d5c94f6b71';

  constructor(private httpClient: HttpClient) {
  }

  scanImage(imageUrl: string) {
    const headers = this.getHeaders(this.subscriptionKey);
    const params = this.getParams();
    return this.httpClient.post<FaceRecognitionResponse>(
      environment.faceAPIEndPoint,
      '{"url": ' + '"' + imageUrl + '"}',
      {
        params,
        headers
      }
    );
  }

  private getHeaders(subscriptionKey: string) {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Ocp-Apim-Subscription-Key', subscriptionKey);

    return headers;
  }

  private getParams() {
    const httpParams = new HttpParams()
      .set('returnFaceId', 'true')
      .set('returnFaceLandmarks', 'false')
      .set(
        'returnFaceAttributes',
        'age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise'
      );

    return httpParams;
  }


}
