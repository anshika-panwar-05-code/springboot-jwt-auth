package com.DevSec.Devops.controller;

import com.DevSec.Devops.dto.PRRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/pr-scan")
public class PRScanController {

    @Autowired
    private RestTemplate restTemplate;

    // Set headers
    HttpHeaders headers = new HttpHeaders();

    @PostMapping("/scan")
    public ResponseEntity<?> scanPullRequest(@RequestBody PRRequest prRequest){
        String nodeBackendUrl="http://localhost:5000/api/scan/pr";
        HttpEntity<PRRequest> request = new HttpEntity<>(prRequest,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                nodeBackendUrl,
                request,
                String.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}
