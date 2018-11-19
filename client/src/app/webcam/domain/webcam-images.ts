export class WebcamImage {
  private _mimeType: string = null;
  private _imageAsBase64: string = null;
  private _imageAsDataUrl: string = null;


  public constructor(imageAsDataUrl: string, mimeType: string) {
    this._mimeType = mimeType;
    this._imageAsDataUrl = imageAsDataUrl;

  }


  public get imageAsBase64(): string {
    return this._imageAsBase64 ?
      this._imageAsBase64 : this._imageAsBase64 = this.getDataFromDataUrl(this._imageAsDataUrl);

  }

  public get imageAsDataUrl(): string {
    return this._imageAsDataUrl;

  }

  private getDataFromDataUrl(dataUrl: string) {
    return dataUrl.replace('data:' + this._mimeType + ';base64,', '');

  }

}
