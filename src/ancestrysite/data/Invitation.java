package ancestrysite.data;

public class Invitation {

	private Long userId;
	private Long groupId;
	private String accept;
	private Long inviteId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public Long getInviteId() {
		return inviteId;
	}
	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}
	
}
