package al.example.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import al.example.enums.OrderStatusesEnum;
import al.example.model.dto.IOrderStatusActionDTO;
import al.example.repo.OrderStatusActionRepo;
import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Test {
	
	private final OrderStatusActionRepo repo;
	
	@org.junit.jupiter.api.Test
	public void test() {
		IOrderStatusActionDTO irder = repo.findOrderStatusActionByOrderStatusName(OrderStatusesEnum.CREATED.getName().toLowerCase()).get();
		
		System.err.println("Approve: " +irder.getAllowApprove());
		System.err.println("Cancel: " +irder.getAllowCancel());
		System.err.println("Edit: " +irder.getAllowEdit());
		System.err.println("Fulfill: " +irder.getAllowFulfill());
		System.err.println("Submit: " +irder.getAllowSubmit());
		
		assertEquals(OrderStatusesEnum.CREATED.getName(), irder.getOrderStatusName());
	}

}
