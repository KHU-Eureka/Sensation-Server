package com.eureka.ationserver.controller;

import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.user.UserRepository;
import com.eureka.ationserver.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(tags = {"MyPage"})
@RequiredArgsConstructor
public class MyPageController {
    private final UserRepository userRepository;
    private final MyPageService myPageService;


    @PostMapping("/mypage/image")
    @ApiOperation(value="마이페이지 커버 이미지 변경")
    public ResponseEntity saveImg(@AuthenticationPrincipal UserDetails userDetails,@RequestParam(value = "myPageImg", required = true) MultipartFile myPageImg) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        return new ResponseEntity(myPageService.saveImg(user, myPageImg), null, HttpStatus.OK);
    }
}
