package cn.chendahai.ray.enums;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    /**
     * 待审核
     */
    NOT_YET,
    /**
     * 审核通过
     */
    PASS,
    /**
     * 审核不通过
     */
    REJECT;

    public static void main(String[] args) {
        System.out.println(AuditStatusEnum.NOT_YET);
        System.out.println(Objects.equals(AuditStatusEnum.NOT_YET, "NOT_YET"));
        System.out.println(Objects.equals(AuditStatusEnum.NOT_YET.name(), "NOT_YET"));
    }
}
