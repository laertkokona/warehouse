package al.example.model.dto;

public interface IOrderStatusActionDTO {
	
	Long getId();
	String getOrderStatusName();
	Boolean getAllowApprove();
	Boolean getAllowCancel();
	Boolean getAllowDeliver();
	Boolean getAllowEdit();
	Boolean getAllowFulfill();
	Boolean getAllowSubmit();

}
