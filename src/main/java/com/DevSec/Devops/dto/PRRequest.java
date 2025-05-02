package com.DevSec.Devops.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PRRequest {
    private String repositoryOwner;
    private String repositoryName;
    private int prNumber;
}
