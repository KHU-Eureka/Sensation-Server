package com.eureka.ationserver.controller;


import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.service.LoungeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"Lounge"})
@RequiredArgsConstructor
public class LoungeController {

  private final LoungeService loungeService;

  @PostMapping("/lounge")
  @ApiOperation("라운지 생성")
  public ResponseEntity save(@RequestBody LoungeRequest loungeRequest){
    return new ResponseEntity(loungeService.save(loungeRequest), null, HttpStatus.CREATED);

  }

  @GetMapping("/lounge")
  @ApiOperation("라운지 목록 조회")
  public ResponseEntity getAll(){
    return new ResponseEntity(loungeService.findAll(), null, HttpStatus.OK);
  }

  @GetMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 조회")
  public ResponseEntity get(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.find(loungeId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 정보 수정")
  public ResponseEntity update(@PathVariable Long loungeId, @RequestBody LoungeRequest loungeRequest){
    return new ResponseEntity(loungeService.update(loungeId, loungeRequest), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/notice")
  @ApiOperation("라운지 공지 수정")
  public ResponseEntity updateNotice(@PathVariable Long loungeId, @RequestBody String notice){
    return new ResponseEntity(loungeService.updateNotice(loungeId, notice), null, HttpStatus.CREATED);

  }

  @DeleteMapping("/lounge/{loungeId}")
  @ApiOperation("라운지 삭제")
  public ResponseEntity delete(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.delete(loungeId), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/close")
  @ApiOperation("라운지 종료")
  public ResponseEntity close(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.close(loungeId), null, HttpStatus.OK);
  }

  @PutMapping("/lounge/{loungeId}/start")
  @ApiOperation("라운지 시작")
  public ResponseEntity start(@PathVariable Long loungeId){
    return new ResponseEntity(loungeService.start(loungeId), null, HttpStatus.OK);

  }

  @PutMapping("/lounge/{loungeId}/member/enter/{memberId}")
  @ApiOperation("라운지 입장")
  public ResponseEntity enter(@PathVariable Long loungeId, @PathVariable Long memberId){
    return null;
  }

  @PutMapping("/lounge/{loungeId}/member/exit/{memberId}")
  @ApiOperation("라운지 퇴장")
  public ResponseEntity exit(@PathVariable Long loungeId, @PathVariable Long memberId){
    return null;
  }

  @GetMapping("/lounge/{loungeId}/chat")
  @ApiOperation("라운지 채팅 조회")
  public ResponseEntity getChat(@RequestBody LoungeRequest loungeRequest){
    return null;

  }

}
