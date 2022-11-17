package io.github.alanaafsc.quarkussocial.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowersPerUseResponse {
    private Integer followersCount;
    private List<FollowerResponse> content;
}
