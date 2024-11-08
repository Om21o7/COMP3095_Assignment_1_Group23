package ca.gbc.approvalservice.service;

import ca.gbc.approvalservice.dto.ApprovalRequest;
import ca.gbc.approvalservice.dto.ApprovalResponse;

import java.util.List;
import java.util.Optional;

public interface ApprovalService {
    String createApproval(ApprovalRequest approvalRequest,boolean isApproved);
    List<ApprovalResponse> getAllApprovals();
    String geteventinfo(String eventId);
    Optional<ApprovalResponse> getApprovalById(Long id);
    Optional<ApprovalResponse> updateApproval(Long id, ApprovalRequest approvalRequest);
    boolean deleteApproval(Long id);
}
