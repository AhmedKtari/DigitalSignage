package io.github.ahmedktarii.degitalesingage.Controllers;


import io.github.ahmedktarii.degitalesingage.DTOS.CreateSignRequest;

import io.github.ahmedktarii.degitalesingage.Entities.signStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sign")
@CrossOrigin
public class CreateSignController {

    @PostMapping("/creat")
    public ResponseEntity<?> CreateSign(@RequestBody CreateSignRequest request ){
               // save sign and save it in DB
                if(request.getSignStatusRequest()== signStatus.online){
                    // create schedule entity
                    // call the function in schedulSignController
                    //schedule it
                }


    }




}
