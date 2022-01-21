package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.insight.InsightRequest;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.insight.*;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.dto.insight.*;
import com.eureka.ationserver.repository.category.MainCategoryRepository;
import com.eureka.ationserver.repository.category.SubCategoryRepository;
import com.eureka.ationserver.repository.insight.*;
import com.eureka.ationserver.repository.user.UserRepository;
import com.eureka.ationserver.util.image.ImageUtil;
import com.eureka.ationserver.util.parse.Parse;
import com.eureka.ationserver.util.parse.ParseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final InsightRepository insightRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final InsightTagRepository insightTagRepository;
    private final InsightSubCategoryRepository insightSubCategoryRepository;
    private final InsightViewRepository insightViewRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long savePublic(InsightRequest insightRequest) throws Exception {

        Parse parse = ParseUtil.parse(Insight.INSIGHT_PREFIX, insightRequest.getUrl());


        // public
        MainCategory mainCategory = mainCategoryRepository.findById(insightRequest.getInsightMainCategoryId()).get();
        List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(insightRequest.getInsightSubCategoryIdList());


        Insight insight = Insight.builder()
            .url(insightRequest.getUrl())
            .title(parse.getTitle())
            .description(parse.getDescription())
            .imgPath(parse.getImageUrl())
            .siteName(parse.getSiteName())
            .icon(parse.getIcon())
            .insightMainCategory(mainCategory)
            .open(true)
            .build();
        Insight saved = insightRepository.save(insight);

        for(SubCategory subCategory : subCategoryList){
            InsightSubCategory insightSubCategory = InsightSubCategory.builder()
                .insight(insight)
                .subCategory(subCategory)
                .build();
            insightSubCategoryRepository.save(insightSubCategory);
        }


        for(String tag : insightRequest.getTagList()){
            InsightTag insightTag = InsightTag.builder()
                .insight(insight)
                .name(tag)
                .build();
            insightTagRepository.save(insightTag);
        }

        return saved.getId();


    }

    @Transactional(readOnly = true)
    public List<InsightResponse> findPublicAll(){
        List<Insight> insightList = insightRepository.findByOpenOrderByCreatedAtDesc(true);
        List<InsightResponse> insightResponseList = new ArrayList<>();
        for(Insight insight : insightList){
            insightResponseList.add(new InsightResponse(insight));
        }
        return insightResponseList;
    }

    @Transactional
    public InsightResponse saveImg(Long insightId, MultipartFile insightImg) throws IOException{
        Insight insight = insightRepository.getById(insightId);

        List<String> pathList = ImageUtil.getImagePath(Insight.INSIGHT_PREFIX, insightId);
        File file = new File(pathList.get(1));
        insightImg.transferTo(file);
        insight.setImgPath(pathList.get(0));


       return new InsightResponse(insight);
    }

    @Transactional()
    public InsightResponse findPublic(User user, Long insightId){
        Insight insight = insightRepository.getById(insightId);

        Optional<InsightView> insightView = insightViewRepository.findByUserAndInsight(user, insight);
        if(!insightView.isPresent()){
            InsightView newInsightView = InsightView.builder()
                    .insight(insight)
                    .user(user)
                    .build();
            insightViewRepository.save(newInsightView);
        }
        return new InsightResponse(insight);
    }

    @Transactional(readOnly = true)
    public List<InsightResponse> findByMainCategory(Long mainCategoryId){
        List<Insight> insightList = insightRepository.findByInsightMainCategoryIdOrderByCreatedAtDesc(mainCategoryId);
        List<InsightResponse> insightResponseList = new ArrayList<>();
        for(Insight insight : insightList){
            insightResponseList.add(new InsightResponse(insight));
        }
        return insightResponseList;
    }

    @Transactional(readOnly = true)
    public Set<InsightResponse> search(String keyword){
        Set<InsightResponse> insightResponseList = insightRepository.findByOpenAndTitleContainingOrInsightMainCategoryNameContainingOrInsightSubCategoryList_SubCategoryNameContainingOrInsightTagList_NameContainingOrderByCreatedAtDesc(true, keyword, keyword, keyword, keyword).stream().map(InsightResponse::new).collect(Collectors.toSet());
        return insightResponseList;
    }




}

