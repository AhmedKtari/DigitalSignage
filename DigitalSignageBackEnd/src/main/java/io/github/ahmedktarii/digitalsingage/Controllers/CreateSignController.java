package io.github.ahmedktarii.digitalsingage.Controllers;


import io.github.ahmedktarii.digitalsingage.DTOS.CreateSignRequest;

import io.github.ahmedktarii.digitalsingage.Entities.Schedule;
import io.github.ahmedktarii.digitalsingage.Entities.Sign;
import io.github.ahmedktarii.digitalsingage.Services.MediaService;
import io.github.ahmedktarii.digitalsingage.Services.ScheduleService;
import io.github.ahmedktarii.digitalsingage.Services.UserService;

import io.github.ahmedktarii.digitalsingage.Services.signService;
import io.github.ahmedktarii.digitalsingage.Utils.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/api/sign")
@CrossOrigin
public class CreateSignController {
    @Autowired
    private UserService userservice;

    @Autowired
    private MediaService mediaService;

    private StringUtility stringUtility;
    @Autowired
    private signService signService;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/createSign")
    ResponseEntity<?> createSign(@RequestBody CreateSignRequest request ) {
        //if u create a sign you have two options publishing it now or schedule it later


        long ownerId=userservice.grapIdByEmail(request.getUserEmailRequest());
        // the slug AKA the unique URL for that spec SIGN
        String slug = StringUtility.generateRandomSlug();
        // Sign Creation
        Sign newSign = Sign.builder()
                .ownerId(ownerId)
                .title(request.getSignTitleRequest())
                .slug(slug)
                .status(request.getSignStatusRequest())
                .createdAt(LocalDateTime.now())
                .build();
        signService.save(newSign);
        // Scheduling the sign
        // if "online" the start time will be the moment of creation that sign
        // if "offline" start time wil be costume
        Schedule newSchedule = Schedule.builder()
                .mediaId(request.getMediaIdRequest())
                .signId(newSign.getId())
                .endTime(request.getSignEndDateRequest())
                .build();
        if(Objects.equals(request.getSignStatusRequest(), "offline")){
                newSchedule.setStartTime(request.getSignStartDateRequest());
        }
        else{
            newSchedule.setStartTime(Timestamp.valueOf(newSign.getCreatedAt()));
        }
        scheduleService.saveSchedule(newSchedule);
        return ResponseEntity.ok(Map.of(
                        "message", "login successful",
                        "url", newSign.getSlug()
                )
        );

    }




}
