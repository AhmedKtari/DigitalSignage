package io.github.ahmedktarii.digitalsingage.Controllers;


import io.github.ahmedktarii.digitalsingage.DTOS.CreateSignRequest;

import io.github.ahmedktarii.digitalsingage.Entities.Sign;
import io.github.ahmedktarii.digitalsingage.Entities.signStatus;
import io.github.ahmedktarii.digitalsingage.Services.UserService;

import io.github.ahmedktarii.digitalsingage.Services.signService;
import io.github.ahmedktarii.digitalsingage.Utils.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/sign")
@CrossOrigin
public class CreateSignController {
    @Autowired
    private UserService userservice;

    private StringUtility stringUtility;
    @Autowired
    private signService signService;

    @PostMapping("/creatignqs")
    ResponseEntity<?> createSign(@RequestBody CreateSignRequest request ) {
        if(!(request.getSignStatusRequest()==signStatus.offline)){
            //the sign wil be added in the sign table

            long ownerId=userservice.grapIdByEmail(request.getUserEmailRequest());

            String slug = StringUtility.generateRandomSlug();

            // needs commons-lang3
            Sign newSign = Sign.builder()
                    .owner(ownerId)
                    .title(request.getSignTitleRequest())
                    .slug(slug)
                    .status(request.getSignStatusRequest())
                    .createdAt(LocalDateTime.now())
                    .build();
            signService.save(newSign);

        }
        else{
            //sign will be scheduled
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();


    }




}
