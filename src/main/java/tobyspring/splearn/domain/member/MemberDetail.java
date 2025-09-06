package tobyspring.splearn.domain.member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import tobyspring.splearn.domain.AbstractEntity;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {
    @Embedded
    private Profile profile;

    private String introduction;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    // 애그리거트는 애그리거트 루트를 통해 값을 조작하거나 접근할 수 있다.
    // 그러므로 public 접근 제어자를 사용하지 않는다.
    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    void setActivatedAt() {
        Assert.isTrue(activatedAt == null, "이미 activatedAt이 설정되어있습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    void deactivate() {
        Assert.isTrue(deactivatedAt == null, "이미 deactivatedAt 설정되어있습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
