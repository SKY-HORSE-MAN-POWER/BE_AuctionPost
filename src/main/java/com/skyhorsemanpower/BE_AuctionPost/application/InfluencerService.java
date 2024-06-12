package com.skyhorsemanpower.BE_AuctionPost.application;

import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerAddRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerUpdateRequestDto;

public interface InfluencerService {

	void addInfluencer(InfluencerAddRequestDto influencerAddRequestDto);

	InfluencerDetailResponseDto findInfluencer(String influencerUuid);

	void updateInfluencer(InfluencerUpdateRequestDto influencerUpdateRequestDto);

	void removeInfluencer(String influencerUuid);
}
