package ancestrysite.data;

public class ParentNotification {

	private Long notificationId;
	private Long userId;
	private String parentFirstName;
	private String parentLastName;
	
	public Long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getParentFirstName() {
		return parentFirstName;
	}
	public void setParentFirstName(String parentFirstName) {
		this.parentFirstName = parentFirstName;
	}
	public String getParentLastName() {
		return parentLastName;
	}
	public void setParentLastName(String parentLastName) {
		this.parentLastName = parentLastName;
	}
	
	
	
}
