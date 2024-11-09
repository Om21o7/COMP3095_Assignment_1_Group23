package ca.gbc.approvalservice.dto;


    public record ApprovalResponse(

    Long id,
    String eventId,
    String approver,
    String status,
    String notes
    ) {
    }
