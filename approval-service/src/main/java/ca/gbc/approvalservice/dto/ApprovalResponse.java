package ca.gbc.approvalservice.dto;


public record ApprovalResponse(

        Long id,            // Unique identifier for the approval
        String eventId,     // ID of the associated event
        String approver,    // Name of the approver
        String status,      // Approval status (e.g., "Pending", "Approved", "Rejected")
        String notes        // Notes from the approver
) {}
