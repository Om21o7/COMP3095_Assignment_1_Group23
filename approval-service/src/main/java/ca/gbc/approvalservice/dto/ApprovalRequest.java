package ca.gbc.approvalservice.dto;


public record ApprovalRequest(
        Long id,
        String eventId,    // ID of the event to be approved
        String approver,   // Name of the approver
        String notes       // Optional notes from the approver
) {}