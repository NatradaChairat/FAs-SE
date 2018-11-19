// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  faceAPIEndPoint: 'https://southeastasia.api.cognitive.microsoft.com/face/v1.0/detect',
  firebase: {
    apiKey: "AIzaSyCCyG-WrKzIY1_ByG2fXeV8aZ5i9mg7Mqo",
    authDomain: "facialauthentication-camt.firebaseapp.com",
    databaseURL: "https://facialauthentication-camt.firebaseio.com",
    projectId: "facialauthentication-camt",
    storageBucket: "facialauthentication-camt.appspot.com",
    messagingSenderId: "41278168735"
  }
};

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
