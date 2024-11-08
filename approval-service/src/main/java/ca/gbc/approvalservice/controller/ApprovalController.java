package ca.gbc.approvalservice.controller;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;
import ca.gbc.approvalservice.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping
    public ResponseEntity<String> createApproval(@RequestParam boolean isApproved,@RequestBody ApprovalRequest approvalRequest) {
        String response = approvalService.createApproval(approvalRequest,isApproved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // Return 201 on success
    }

    @GetMapping
    public List<ApprovalResponse> getAllApprovals() {
        return approvalService.getAllApprovals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprovalResponse> getApprovalById(@PathVariable Long id) {
        Optional<ApprovalResponse> response = approvalService.getApprovalById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApprovalResponse> updateApproval(@PathVariable Long id, @RequestBody ApprovalRequest approvalRequest) {
        Optional<ApprovalResponse> response = approvalService.updateApproval(id, approvalRequest);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<String> getEeventInfo(@RequestParam String id) {
        String info = approvalService.geteventinfo(id);
        return ResponseEntity.ok(info);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        if (approvalService.deleteApproval(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
