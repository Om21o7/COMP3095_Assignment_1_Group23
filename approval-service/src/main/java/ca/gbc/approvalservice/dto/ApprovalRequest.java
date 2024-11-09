package ca.gbc.approvalservice.dto;


public record ApprovalRequest(
        Long id,
        String eventId,
        String approver,
        String notes
) {}