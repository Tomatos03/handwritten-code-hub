package com.codehub.chatroom.common.message;

/**
 * 消息类型枚举
 *
 * 协议格式: [Length(4B)] [Type(1B)] [JSON Body]
 * Type 字段对应此枚举的 code 值
 */
public enum MessageType {

    CHAT(1),
    PRIVATE(2),
    SYSTEM(3),
    COMMAND(4);

    private final int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 根据 code 查找对应的 MessageType
     *
     * @param code 协议中的 Type 字段值
     * @return 对应的枚举值
     * @throws IllegalArgumentException code 不合法时抛出
     */
    public static MessageType fromCode(int code) {
        // TODO: 实现 code → 枚举的查找
        throw new UnsupportedOperationException("待实现");
    }
}
