package com.eureka.ationserver.controller;

import com.eureka.ationserver.domain.user.User;
import com.eureka.ationserver.dto.pin.InsightPinRequest;
import com.eureka.ationserver.dto.pin.PinRequest;
import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.dto.pin.PinUpdateRequest;
import com.eureka.ationserver.repository.UserRepository;
import com.eureka.ationserver.service.PinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Api(tags = {"Pin"})
@RequiredArgsConstructor
public class PinController {

    private final UserRepository userRepository;
    private final PinService pinService;

    @PostMapping("/pin")
    @ApiOperation(value="핀 생성")
    public ResponseEntity saveNewPin(@AuthenticationPrincipal UserDetails userDetails, @RequestBody InsightPinRequest insightPinRequest) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinService.saveNewPin(user, insightPinRequest),null, HttpStatus.CREATED);
    }

    @PostMapping("/pin/up")
    @ApiOperation(value="인사이트 핀업")
    public ResponseEntity pinUp(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PinRequest pinRequest){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinService.pinUp(user, pinRequest),null, HttpStatus.CREATED);
    }

    @PutMapping("/pin/{pinId}")
    @ApiOperation(value="핀 수정")
    public ResponseEntity update(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PinUpdateRequest pinUpdateRequest, @PathVariable Long pinId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinService.update(user, pinUpdateRequest, pinId), null, HttpStatus.OK);
    }

    @DeleteMapping("/pin/{pinId}")
    @ApiOperation(value="핀 삭제")
    public ResponseEntity delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long pinId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinService.delete(user, pinId), null, HttpStatus.OK);
    }

    @GetMapping("/pin/{pinId}")
    @ApiOperation(value="핀 조회")
    public ResponseEntity find(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long pinId){
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(pinService.find(user, pinId), null, HttpStatus.OK);
    }
    @GetMapping("/pin/search")
    @ApiOperation(value="핀 검색")
    public ResponseEntity search(@RequestParam Long personaId, @RequestParam String keyword){
        return new ResponseEntity(pinService.search(personaId, keyword), null, HttpStatus.OK);
    }

    @GetMapping("/pin/pin-board/{pinBoardId}")
    @ApiOperation(value="핀보드별 핀 조회")
    public ResponseEntity findByPinBoard(@PathVariable Long pinBoardId){
        return new ResponseEntity(pinService.findByPinBoard(pinBoardId), null, HttpStatus.OK);
    }
}