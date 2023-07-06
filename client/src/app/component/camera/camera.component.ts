import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { WebcamComponent, WebcamImage } from 'ngx-webcam';
import { Subject, Subscription } from 'rxjs';
import { CameraService } from 'src/app/service/camera.service';

@Component({
  selector: 'app-camera',
  templateUrl: './camera.component.html',
  styleUrls: ['./camera.component.css']
})
export class CameraComponent implements OnInit, OnDestroy, AfterViewInit {
  //npm install webcam and add to app.module.ts
  @ViewChild(WebcamComponent) webcam!: WebcamComponent;
  width=400; 
  height=400; 
  pics: string[] = []; 
  subscription!: Subscription; 
  trigger = new Subject<void>; 

  constructor(private router: Router, private cameraSvc: CameraService){}
  
  //upon intialisation of the website
  ngOnInit(): void {    
    
  }
  
  //upon destroy
  ngOnDestroy(): void {
    //stop subscribing 

  }
  
  ngAfterViewInit(): void {
      this.webcam.trigger = this.trigger; 
      //in this instance of subscription, webcam module.imageCapture will subscribe to 
      this.subscription = this.webcam.imageCapture.subscribe(
        //bind(): creates a copy of function with new value of `this` inside the calling function
          //in this instance of method snapshot
          //create a copy of function of snapshot
          //and webcamImage will be attached with the value of `this`: refers to the global object
        this.snapshot.bind(this)
        )
    }


  //adds image (as data url) from webcamImage (module) into the pics array
  snapshot(webcamImage: WebcamImage){
    //assign this instance of cameraSvc.imageData to webcamImage.imageAsDataUrl (from ngx-webcam)
    this.cameraSvc.imageData = webcamImage.imageAsDataUrl; 
    //add the instance to pics string array (string[])
    this.pics.push(this.cameraSvc.imageData); 
  }

  snap(){
    this.trigger.next(); 
  }


}
