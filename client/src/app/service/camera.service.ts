import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CameraService {
  //initialise an empty string first before putting any data in 
  imageData = ""; 

  constructor() { }
}
