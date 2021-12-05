package com.eureka.ationserver.dto.pin;


import com.eureka.ationserver.domain.insight.Pin;
import com.eureka.ationserver.dto.insight.InsightResponse;
import com.eureka.ationserver.dto.pinBoard.PinBoardResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinResponse {
    private Long id;

    private PinBoardResponse pinBoard;

    private InsightPinResponse insight;

    private List<String> tagList;

    public PinResponse(Pin pin){
        this.id = pin.getId();
        this.pinBoard = new PinBoardResponse(pin.getPinBoard());
        this.insight = new InsightPinResponse(pin.getInsight());
        List<String> tagList = new ArrayList<>();
        pin.getPinTagList().stream().forEach(x-> tagList.add(x.getName()));
        this.tagList = tagList;
    }
}