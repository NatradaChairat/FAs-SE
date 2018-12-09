import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable()
export class FaceRecognitionService {

  subscriptionKey = '8f046261e7b14328b440b5729fce01bb';

  constructor(private httpClient: HttpClient) {
  }

  // return personId
  createPersonInLargePersonGroup(personName: string) {
    const headers = this.getHeaders(this.subscriptionKey);
    const params = this.getParams();
    return this.httpClient.post<FaceRecognitionResponse>(
      environment.faceAPIEndPoint + 'largepersongroups/sefasproject/persons',
      '{"name": "' + personName + '"}',
      {
        params,
        headers
      }
    );
  }

  // return persistedFaceId
  addFaceInLargePersonGroup(personId: string, imageUrl: string) {
    const headers = this.getHeaders(this.subscriptionKey);
    const params = this.getParams();
    return this.httpClient.post<FaceRecognitionResponse>(
      environment.faceAPIEndPoint + 'largepersongroups/sefasproject/persons/' + personId + '/persistedfaces',
      '{"url": "' + imageUrl + '"}',
      {
        params,
        headers
      }
    );
  }

  updateFaceInLargePersonFroup(personId: string, persistedFaceId: string) {

  }

  trainLargePersonGroup() {
    const headers = this.getHeaders(this.subscriptionKey);
    const params = this.getParams();
    return this.httpClient.post<FaceRecognitionResponse>(
      environment.faceAPIEndPoint + 'largepersongroups/sefasproject/train/',
      '',
      {
        params,
        headers
      }
    );
  }

  detectImage(imageUrl: string) {
    const headers = this.getHeaders(this.subscriptionKey);
    const params = this.getParams();
    return this.httpClient.post<FaceRecognitionResponse>(
      environment.faceAPIEndPoint + 'detect',
      '{"url": ' + '"' + imageUrl + '"}',
      {
        params,
        headers
      }
    );
  }

  // return personId candidates that confidence no. under 0.7
  // use personId to find name
  identifyImages(faceId: string) {
    const headers = this.getHeaders(this.subscriptionKey);
    const params = this.getParams();
    return this.httpClient.post<FaceRecognitionResponse>(
      environment.faceAPIEndPoint + 'identify',
      '{"largePersonGroupId": "sefasproject",' +
      '"faceIds": ["' + faceId + '"],' +
      '"maxNumOfCandidatesReturned": 1,' +
      '"confidenceThreshold": 0.7 }',
      {
        params,
        headers
      }
    );
  }

  // return personId, persistedFaceIds, name, userData
  getPersonInLargePersonGroup(personId: string) {
    const headers = this.getHeaders(this.subscriptionKey);
    return this.httpClient.get<FaceRecognitionResponse>(
      environment.faceAPIEndPoint + 'largepersongroups/sefasproject/persons/' + personId,
      {
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
