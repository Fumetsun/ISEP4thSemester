package jobs4u.base.customermanagement.domain.dto;

public class CustomerDTO {

	private String customerCode;
	private String customerName;
	private String customerAddress;
	private String customerManagerUser;
	private String customerUser;

	public CustomerDTO (String code, String name, String address, String manager, String user) {
		customerCode = code;
		customerName = name;
		customerAddress = address;
		customerManagerUser = manager;
		customerUser = user;
	}

	public String toString() {
		return String.format(
				"Customer info:\n\t- Code: %s\n\t- Name: %s\n\t- Address: %s\n\t- User: %s\n\t- Manager: %s\n",
				customerCode, customerName, customerAddress, customerUser, customerManagerUser);
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public String getCustomerManager() {
		return this.customerManagerUser;
	}

	public String getCustomerUser() {
		return this.customerUser;
	}

}
