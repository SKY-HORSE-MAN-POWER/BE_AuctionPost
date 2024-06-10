package com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate.impl;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.SearchAllAuctionDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.read.ReadAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.mongotemplate.CustomReadAuctionPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomReadAuctionPostRepositoryImpl implements CustomReadAuctionPostRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ReadAuctionPost> findAllAuctionPost(SearchAllAuctionDto searchAllAuctionDto, Pageable pageable) {
        log.info("SearchAllAuctionDto >>> {}", searchAllAuctionDto.toString());

        Criteria criteria = new Criteria();

        if (searchAllAuctionDto.getAuctionState() != null) {
            criteria.and("state").is(searchAllAuctionDto.getAuctionState());
        }
        if (searchAllAuctionDto.getTitle() != null) {
            criteria.and("title").regex(searchAllAuctionDto.getTitle(), "i");
        }
        if (searchAllAuctionDto.getLocalName() != null) {
            criteria.and("localName").regex(searchAllAuctionDto.getLocalName(), "i");
        }
        if (searchAllAuctionDto.getInfluencerName() != null) {
            criteria.and("influencerName").regex(searchAllAuctionDto.getInfluencerName(), "i");
        }

        Query query = new Query(criteria).with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize());

        log.info("Qeury >>> {}", query);

        List<ReadAuctionPost> auctionPosts = mongoTemplate.find(query, ReadAuctionPost.class);

        return PageableExecutionUtils.getPage(
                auctionPosts,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), ReadAuctionPost.class)
        );
    }
}