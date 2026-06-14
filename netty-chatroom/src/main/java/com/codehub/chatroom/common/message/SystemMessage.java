package com.codehub.chatroom.common.message;

/**
 * 系统消息
 *
 * 服务端发送 → 客户端显示
 * 用途：上下线通知、欢迎语、错误提示
 */
public final class SystemMessage extends Message {

    private final String content;

    public SystemMessage(String sender, String content) {
        super(MessageType.SYSTEM, sender);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("[系统] %s", content);
    }
}
